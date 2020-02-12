-- -----------------------------------------------------
-- prepared statements
-- -----------------------------------------------------

-- -----------------------------------------------------
-- insert user role
-- -----------------------------------------------------
INSERT INTO user_role(name) VALUES (?)
INSERT INTO user_role(name) VALUES ("Admin")
INSERT INTO user_role(name) VALUES ("User")

-- -----------------------------------------------------
-- insert user
-- -----------------------------------------------------
-- if empty
SELECT * from user where login = ?
--then
INSERT INTO user(login, username, role_id, password_hash, money, cookie, is_available) 
VALUES (?, ?, (SELECT id from user_role where name = "User"), ?, ?, ?, true)

INSERT INTO user(login, username, role_id, password_hash, money, cookie, is_available) 
VALUES ("admin", "Kiselevich Ivan", (SELECT id from user_role where name = "Admin"), "1234", "999.99", "cookie_test", true);
-- else if 
SELECT * from user where login = ? and is_available = false
UPDATE user
SET login = ?, username = ?, role_id = ()
WHERE id = (SELECT id from user where login = ? and is_available = false)



