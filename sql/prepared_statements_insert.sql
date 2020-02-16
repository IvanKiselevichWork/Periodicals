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
INSERT INTO user(login, username, role_id, password, money, is_available)
VALUES (?, ?, (SELECT id from user_role where name = "User"), ?, ?, true)

INSERT INTO user(login, username, role_id, password, money, is_available)
VALUES ("admin", "Kiselevich Ivan", (SELECT id from user_role where name = "Admin"), "1234", "999.99", true);
-- else if 
SELECT * from user where login = ? and is_available = false
UPDATE user
SET login = ?, username = ?, role_id = (SELECT id from user_role where name = "User"), password = ?, money = ?, is_available = true
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

-- -----------------------------------------------------
-- insert payment_type
-- -----------------------------------------------------
INSERT INTO payment_type(type) VALUES (?)
INSERT INTO payment_type(type) VALUES ("refill")
INSERT INTO payment_type(type) VALUES ("payment")
INSERT INTO payment_type(type) VALUES ("refund")

-- -----------------------------------------------------
-- insert payment
-- -----------------------------------------------------
INSERT INTO payment(user_id, type_id, date, amount, subscription_id)
VALUES(?, (SELECT type FROM payment_type WHERE type = ?), ?, ?, ?)

INSERT INTO payment(user_id, type_id, date, amount)
VALUES(1, (SELECT id FROM payment_type WHERE type = "refill"), "2020-02-13 13:00:00", 999.99)

-- -----------------------------------------------------
-- insert subscription
-- -----------------------------------------------------
INSERT INTO subscription(edition_id, user_id, subscription_start_date, subscription_end_date)
VALUES(?, ?, ?, ?)

INSERT INTO subscription(edition_id, user_id, subscription_start_date, subscription_end_date)
VALUES(1, 1, "2020-03-00 00:00:00", "2020-09-00 00:00:00");

-- -----------------------------------------------------
-- payment for subscription
-- -----------------------------------------------------
INSERT INTO payment(user_id, type_id, date, amount, subscription_id)
VALUES(1, (SELECT id FROM payment_type WHERE type = "payment"), "2020-02-13 14:00:00", 35.88, 1)

UPDATE user
SET money = (money - 35.88)
WHERE id = 1