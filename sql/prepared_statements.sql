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
SET login = ?, username = ?, role_id = (SELECT id from user_role where name = "User"), password_hash = ?, money = ?, cookie = ?, is_available = true
WHERE id = (SELECT id from user where login = ? and is_available = false)

-- -----------------------------------------------------
-- insert edition type
-- -----------------------------------------------------
INSERT INTO edition_type(name) VALUES (?)
INSERT INTO edition_type(name) VALUES ("Journal")
INSERT INTO edition_type(name) VALUES ("Newspaper")
INSERT INTO edition_type(name) VALUES ("Book")

-- -----------------------------------------------------
-- insert edition theme
-- -----------------------------------------------------
INSERT INTO edition_theme(name) VALUES (?)
INSERT INTO edition_theme(name) VALUES ("Auto")
INSERT INTO edition_theme(name) VALUES ("Business")
INSERT INTO edition_theme(name) VALUES ("Military")

-- -----------------------------------------------------
-- insert edition
-- -----------------------------------------------------
INSERT INTO edition(name, type_id, theme_id, periodicity_per_year, minimum_subscription_period_in_months, price_for_minimum_subscription_period) 
VALUES(?, (SELECT id from edition_type where name = ?), (SELECT id from edition_theme where name = ?), ?, ?, ?)

INSERT INTO edition(name, type_id, theme_id, periodicity_per_year, minimum_subscription_period_in_months, price_for_minimum_subscription_period) 
VALUES("Мир тяжелых моторов", (SELECT id from edition_type where name = "Newspaper"), (SELECT id from edition_theme where name = "Auto"), 24, 6, 35.88);
