/**
* Sistema para gestão de OS 
* @autor Thaina Minga
*/
show databases;
create database dbsistema;
use dbsistema;
show tables;

-- unique (não permite valores iguais no campo)
create table usuarios(
id int primary key auto_increment,
nome varchar(50) not null,
login varchar(15)not null unique,
senha varchar(250)not null,
perfil varchar(10)not null

);
describe usuarios;
drop table usuarios;

-- busca avancada (estilo google)
select * from usuarios where nome like 't%' order by nome;

-- uso do md5() para criptografar uma senha
insert into usuarios (nome,login,senha,perfil)
values ('thaina jamile','thai',md5('123'),'thai');

delete from usuarios where id =2;

select* from usuarios where login ="thaithai";
select * from usuarios;
describe usuarios;

insert into usuarios (nome,login,senha,perfil)
values ('Administrador','Admin',md5('admin'), 'admin');

-- login(autenticacao)
select * from usuarios;
select * from usuarios where nome ="Administrador";
select * from usuarios where login ="Admon";

select * from usuarios where login ='Admon' and senha = md5('admin');

create table clientes(
idcli int primary key auto_increment,
nome varchar(50) not null,
rg varchar(9)not null unique,
cpf varchar(11)not null unique,
fone varchar(15)not null,
cidade varchar(30)not null,
endereco varchar(150)not null,
bairro varchar(30)not null,
cep varchar(10),
numero varchar(10)not null,
complemento varchar(20),
uf char(2),
email varchar(100)
);

insert into clientes (nome,rg,cpf,fone,cidade,endereco,bairro,cep,numero,complemento,uf,email)
values ('thaina','111','111','111','sao paulo','endereco','bairro','111','111','complemento','sp','email');
insert into clientes (nome,rg,cpf,fone,cidade,endereco,bairro,cep,numero,complemento,uf,email)
values ('aina','1113','1113','1113','sao paulo','endereco','bairro','11133','11133','complemento','sp','email');

drop table clientes;
select * from clientes;

/* Relacionsmento de tabelas 1 - N */
-- timestamp default 
--
--
--
create table servicos(
os int primary key auto_increment,
dataOS timestamp default current_timestamp,
equipamento varchar(200) not null,
defeito varchar(200) not null,
valor decimal(10,2),
idcli int not null,
foreign key (idcli) references clientes(idcli)
);

insert into servicos (equipamento, defeito, valor,idcli)
values ('Notebook LeNovo G90','Troca de fone',250,1);

select * from servicos
inner join clientes
on servicos.idcli= clientes.idcli;

 -- (nome,rg,cpf,fone,cidade,endereco,bairro,numero) (os,dataOS,equipamento,defeito,valor,idcli)
 
 /**Relatórios**/
 
 -- clientees
select nome, fone,email from clientes order by nome;

-- servicos
select
servicos.os, servicos.dataOS,servicos.equipamento, servicos.defeito,
servicos.valor,
clientes.nome
from servicos
inner join clientes
on servicos.idcli= clientes.idcli;

-- ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
-- !!!!!!!!!!!!!!!!!!DESAFIOOOOOOOOOOO!!!!!!!!!!!!!!!!!!

create table fornecedores(
idfor int primary key auto_increment,
nome varchar(50) not null,
cnpj varchar(14)not null unique,
fone varchar(15)not null,
cidade varchar(30)not null,
endereco varchar(150)not null,
bairro varchar(30)not null,
cep varchar(10),
numero varchar(10)not null,
complemento varchar(20),
uf char(2),
email varchar(100)
);

select * from fornecedores;

create table produtos(
codp int primary key auto_increment,
idfor  varchar(100),
barcode varchar(100),
descricao varchar(100),
foto longblob ,
estoque int,
estoquemin int,
valor decimal,
um char(2),
locl varchar(50),
nome varchar(50)
);
drop table produtos;

select * from produtos;
-- (UM = unidade de medida),(CODP = codigo do produto),(loc = local armazenamento). 
-- //codp,idfor,barcode,descricao,foto,estoque,estoquemin,valor,um,locl