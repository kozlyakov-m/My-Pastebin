package com.goodline.pastebin;

import com.goodline.pastebin.controller.PasteController;
import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.model.Type;
import com.goodline.pastebin.repos.PasteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PasteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasteController pasteController;

    @MockBean
    private PasteRepository repository;

    @BeforeEach
    public void prepareAuth() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getName()).thenReturn("testUser");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("На главной 10 или меньше паст")
    @Sql(value = {"/create-test-pastes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-test-pastes.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetTen() throws Exception {
        List<Paste> result = pasteController.getTen();
        Assertions.assertTrue(result.size() <= 10);
    }

    @Test
    @DisplayName("Попытка отправить пустую пасту")
    public void postEmptyPaste() throws Exception {
        this.mockMvc.perform(post("/api"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Передан json только с полем text")
    public void createSimplePaste() throws Exception {
        Paste paste = new Paste("text", null);
        pasteController.newPaste(paste);
        Assertions.assertNotNull(paste.getHash());
        Mockito.verify(repository).save(paste);
    }

    @Test
    @DisplayName("Паста создана от имени testUser")
    public void createPasteWithAuthor() throws Exception {

        Paste paste = new Paste("text", null);
        pasteController.newPaste(paste);

        Assertions.assertEquals("testUser", paste.getAuthor());

        Assertions.assertNotNull(paste.getHash());
        Mockito.verify(repository).save(paste);
    }

    @Test
    @DisplayName("Попытка создать пасту от чужого имени")
    public void createPasteWithWrongAuthor() throws Exception {
        Paste paste = new Paste("text", null);
        paste.setAuthor("notTestUser");

        pasteController.newPaste(paste);

        Assertions.assertEquals("testUser", paste.getAuthor());

        Assertions.assertNotNull(paste.getHash());
        Mockito.verify(repository).save(paste);
    }

    @Test
    @DisplayName("Анонимная паста")
    public void createPasteWithoutAuthor() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getName()).thenReturn(null);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Paste paste = new Paste("text", null);
        paste.setAuthor("notAnonymousUser");

        pasteController.newPaste(paste);

        Assertions.assertNull(paste.getAuthor());

        Assertions.assertNotNull(paste.getHash());
        Mockito.verify(repository).save(paste);
    }

    @Test
    @DisplayName("Анонимная паста 2")
    public void createPasteWhenNotAuthenticated() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Paste paste = new Paste("text", null);
        paste.setAuthor("notAnonymousUser");

        pasteController.newPaste(paste);

        Assertions.assertNull(paste.getAuthor());

        Assertions.assertNotNull(paste.getHash());
        Mockito.verify(repository).save(paste);
    }

    @Test
    @DisplayName("Попытка добавить пасту без текста")
    public void emptyPaste() {
        Paste paste = new Paste(null, null);

        ResponseEntity<String> result;
        result = pasteController.newPaste(paste);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    @Test
    @DisplayName("Попытка получить приватную пасту без авторизации")
    public void getPrivateWhenNotAuthenticated() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(false);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        Paste paste = new Paste("text", null);
        paste.setType(Type.PRIVATE);
        Mockito.when(repository.findByHash("1")).thenReturn(paste);

        this.mockMvc.perform(get("/api/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Получить свою приватную пасту по ссылке")
    public void getOwnPaste() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getName()).thenReturn("testUser");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);


        Paste paste = new Paste("text", null);
        paste.setType(Type.PRIVATE);
        paste.setAuthor("testUser");
        Mockito.when(repository.findByHash("1")).thenReturn(paste);

        this.mockMvc.perform(get("/api/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получить свою приватную пасту по ссылке")
    public void getUnlistedPaste() throws Exception {

        Paste paste = new Paste("text", null);
        paste.setType(Type.UNLISTED);
        paste.setAuthor("notTestUser");
        Mockito.when(repository.findByHash("1")).thenReturn(paste);

        this.mockMvc.perform(get("/api/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получить свою приватную пасту по ссылке")
    public void getNotExistingPaste() throws Exception {

        Mockito.when(repository.findByHash("1")).thenReturn(null);

        this.mockMvc.perform(get("/api/1"))
                .andExpect(status().isNotFound());
    }
}