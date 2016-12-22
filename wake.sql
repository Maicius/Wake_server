create database clock;
use clock;
create table appuser(
	user_id int not null AUTO_INCREMENT,
	name varchar(255),
	password varchar(255),
	phone varchar(11),
	primary key(user_id)
)AUTO_INCREMENT=10000;
create table getuptime(
	time_id int primary key,
	user_id int references appuser(user_id),
	time Timestamp
);

insert into appuser(name, password) values("maicius","yuanhao110110");
	create table maiciusTest(
	name varchar(255) primary key,
	password varchar(255),
	phone varchar(11)
);
create database maiciusTest;
use maiciusTest;
create table maiciusTest(
	name varchar(255) primary key,
	password varchar(255),
	phone varchar(11)
);
insert into maiciusTest(name, password)values("袁豪"，"110");

	mysql> select * from appuser;
+---------------+---------------+-------------+
| name          | password      | phone       |
+---------------+---------------+-------------+
| 12254444      | drffff        | NULL        |
| 13076062593   | 19980329      | NULL        |
| 1360368496    | He690903      | NULL        |
| 15754306361   | liuqiuyu      | NULL        |
| 18382012127   | 645734        | null        |
| 2014141463250 | 123456        | NULL        |
| 492539294     | 492539294     | NULL        |
| 494240782     | 494240782     | NULL        |
| 925691117     | zywswswws     | null        |
| ??            | 110110        | NULL        |
| ????          | 123456789     | NULL        |
| ??????        | 123456        | NULL        |
| Chris         | 1234567890    | NULL        |
| hello         | 110           | NULL        |
| huhuhu        | hyq199956     | NULL        |
| hutao         | hutao         | 2295421485  |
| maicius       | yuanhao110110 | 18996720676 |
| maicius1      | yuan          | NULL        |
| maizi         | yuan          | NULL        |
| test          | 110110        | null        |
| test2         | 110110        | NULL        |
| yh            | 110110        | NULL        |
| yuam          | 110           | NULL        |
| yuan          | 110110        | NULL        |
+---------------+---------------+-------------+
insert into appuser(name,password)values("12254444","drffff");
insert into appuser(name,password)values("13076062593","19980329");
insert into appuser(name,password)values("1360368496","He690903");
insert into appuser(name,password)values("15754306361","liuqiuyu");
insert into appuser(name,password)values("18382012127","645734");
insert into appuser(name,password)values("2014141463250","123456");
insert into appuser(name,password)values("492539294","492539294");
insert into appuser(name,password)values("494240782","494240782");
insert into appuser(name,password)values("925691117","zywswswws");
insert into appuser(name,password)values("Chris","1234567890");
insert into appuser(name,password)values("huhuhu","hyq199956");	
insert into appuser(name,password,phone)values("hutao","hutao","2295421485");
insert into appuser(name, password)values("袁豪"，"110110");	


/*****New database*************/
create database clock;
use clock;
create table appuser(
username varchar(255) primary key,
password varchar(255),
nickname varchar(255),
brief_intro varchar(255)
);

create table getuptime(
	time_id int primary key,
	username varchar(255) references appuser(username),
	up_time Timestamp
);
insert into appuser(username, password, nickname) values("18996720676","110110","麦子");

select up_time from getuptime where username = "麦子";
