CREATE DATABASE airlinemanagementsystem;

use airlinemanagementsystem;

create table login(
							username varchar(20),
                            password varchar(20)
				   );
                   
insert into login values("admin","admin");
select * from login;
create table passenger(
							name varchar(20),
                            nationality varchar(20),
                            phone bigint(10),
                            address varchar(40),
                            aadhar bigint(10),
                            gender varchar(5)
					   );

create table flight(
							f_code varchar(20),
                            f_name varchar(20),
                            source varchar(40),
                            destination varchar (30)
				   );

insert into flight values("1001", "AI-1212", "Delhi", "Mumbai");
insert into flight values("1002", "AI-1453", "Delhi", "Goa");
insert into flight values("1003", "AI-1112", "Mumbai", "Chennai");
insert into flight values("1004", "AI-3222", "Delhi", "Amritsar");
insert into flight values("1005", "AI-1212", "Delhi", "Ayodhya");

create table reservation (
								PNR varchar(15),
                                TICKET varchar(20),
                                aadhar varchar(20),
                                name varchar(20),
                                nationality varchar(20),
                                flightname varchar(15),
                                flightcode varchar(20),
                                src varchar(30),
                                des varchar(30),
                                ddate varchar(30)
						 );


create table cancellation(
							pnr varchar(20),
                            name varchar(20),
                            cancelno varchar(20),
                            ftcode varchar(20),
                            ddate varchar(30)
						);