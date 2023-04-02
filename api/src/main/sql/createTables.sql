create schema cinescope;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table cinescope.users (
    userId int primary key GENERATED ALWAYS AS IDENTITY,
    token varchar(100) DEFAULT uuid_generate_v4(),
    name varchar(100) NOT NULL,
    email varchar(100) UNIQUE NOT NULL,
    password varchar(100) NOT NULL,                         -- encrypted
    state varchar(10) NOT NULL check (state in ('Active', 'Inactive'))
);

create table cinescope.movieslist (
    
);