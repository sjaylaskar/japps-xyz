create database ticketeer;
create user 'xyzticketeer' identified by 'xyzticketeer';
grant all on ticketeer.* to 'xyzticketeer';

drop schema ticketeer;

