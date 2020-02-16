-- -----------------------------------------------------
-- prepared statements select
-- -----------------------------------------------------

-- -----------------------------------------------------
-- check if user with login exist
-- -----------------------------------------------------
select * from user where login = ?

-- -----------------------------------------------------
-- check user for sign in
-- -----------------------------------------------------
select id, login, username, role_id, password, money, is_available from user where login = ? and password = ?

-- -----------------------------------------------------
-- show user his subscriptions
-- -----------------------------------------------------
select e.name, e.type, s.subscription_start_date, s.subscription_end_date, e.periodicity_per_year 
from user u join subscription s on u.id = s.user_id join edition e on s.edition_id = e.id 
where u.id = ?
order by 3;

-- -----------------------------------------------------
-- show user his payments
-- -----------------------------------------------------
select p.date, t.type, p.amount, case when p.subscription_id is not null then e.name else "-" end
from user u 
    join payment p on u.id = p.user_id 
    left join subscription s on p.subscription_id = s.id 
    left join edition e on s.edition_id = e.id 
    join payment_type t on p.type_id = t.id
where u.id = ?
order by 1;

-- -----------------------------------------------------
-- show editions by type and theme, and if user subscribed
-- -----------------------------------------------------
select e.name, t.title, e.type, 
    case 
        when u.id = ? then "Yes" 
        else "No" 
    end as is_subscribed
from edition e 
    join edition_theme t on t.id = e.theme_id
    left join subscription s on s.edition_id = e.id
    left join user u on s.user_id = u.id
where e.type in (?, ?) and t.id in (?, ?, ?)
order by 1;

-- -----------------------------------------------------
-- show user profile
-- -----------------------------------------------------
select u.username, u.money, count(s.id) as subscription_count
from user u 
    left join subscription s on u.id = s.user_id
where u.id = ?
group by u.id;



