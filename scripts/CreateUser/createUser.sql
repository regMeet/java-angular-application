--select * from mysql.user;

CREATE USER 'angularproject'@'localhost' IDENTIFIED BY 'angularproject';
CREATE DATABASE IF NOT EXISTS `angular`;
GRANT ALL PRIVILEGES ON angular.* TO 'angularproject'@'localhost';
--grant all privileges on angular.* to angularproject@'%' identified by 'angularproject';
--grant all privileges on angular.* to angularproject@localhost identified by 'angularproject';
--GRANT ALL PRIVILEGES ON * . * TO 'angularproject'@'localhost';
FLUSH PRIVILEGES;