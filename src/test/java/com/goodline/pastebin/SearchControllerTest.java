package com.goodline.pastebin;

import com.goodline.pastebin.controller.SearchController;
import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.repos.PasteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@Sql(value = {"/create-test-pastes.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-test-pastes.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SearchControllerTest {

    @Autowired
    private SearchController searchController;

    @Autowired
    private PasteRepository repository;


    @BeforeEach
    public void prepareAuth() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getName()).thenReturn("user");

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Поиск b без аутентификации должен вернуть все публичные bbb, bbb, BBB, b")
    public void anonymousSearchForB() {
        Authentication authentication = Mockito.mock(Authentication.class);
        //пользователь не аутентифицирован
        Mockito.when(authentication.isAuthenticated()).thenReturn(false);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<Paste> result = searchController.searchByText("b");
        Assertions.assertEquals(4, result.size());
    }

    @Test
    @DisplayName("Поиск a от имени user должен вернуть 5 паст")
    public void userSearchForA() {

        List<Paste> result = searchController.searchByText("a");
        Assertions.assertEquals(5, result.size());
    }
}
