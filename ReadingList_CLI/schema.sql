create table Book (
    id int(5) identity,
    reader varchar(20) NOT NULL,
    isbn  varchar(10) NOT NULL,
    title varchar(50) NOT NULL,
    author varchar(50) NOT NULL,
    description varchar(200) NOT NULL
);