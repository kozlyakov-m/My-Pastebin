delete from paste;

insert into paste (author, expire_date, hash, text, type)
values
('user', null, '1', 'aaa', 0),
('user', null, '2', 'AAA', 0),
('user', null, '3', 'bbb', 0),
(null, null, '4', 'bbb', 0),
(null, null, '5', 'BBB', 0),
('user', null, '6', 'aaa', 1),
('user', null, '7', 'aaa', 2),
('notuser', null, '8', 'aaa', 1),
('notuser', null, '9', 'aaa', 2),
('notuser', null, '10', 'a', 0),
('notuser', null, '11', 'b', 0);