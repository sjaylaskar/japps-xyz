create database ticketeer;
create user 'xyzticketeer' identified by 'xyzticketeer';
grant all on ticketeer.* to 'xyzticketeer';

drop schema ticketeer;

create database ticketeer;
grant all on ticketeer.* to 'xyzticketeer';

use ticketeer;