use ddhomes;
show tables;

/* ================ 기존 테이블 컬럼 수정 ================ */
-- houseinfo 테이블 필요없는 컬럼 제거
ALTER TABLE `houseinfo`
DROP COLUMN `roadNameBubun`,
DROP COLUMN `roadNameSeq`,
DROP COLUMN `roadNameBasementCode`,
DROP COLUMN `roadNameCode`,
DROP COLUMN `bonbun`,
DROP COLUMN `bubun`,
DROP COLUMN `landCode`;

-- housedeal 테이블 필요없는 컬럼 제거
ALTER TABLE `housedeal`
DROP COLUMN `cancelDealType`;

-- houseinfo 컬럼 순서 변경
ALTER TABLE `houseinfo` MODIFY COLUMN `sigunguCode` 	VARCHAR(5) 	AFTER `aptCode`;
ALTER TABLE `houseinfo` MODIFY COLUMN `eubmyundongCode` VARCHAR(5) 	AFTER `sigunguCode`;
ALTER TABLE `houseinfo` MODIFY COLUMN `dongCode` 		VARCHAR(10) AFTER `eubmyundongCode`;
ALTER TABLE `houseinfo` MODIFY COLUMN `dong` 			VARCHAR(40) AFTER `dongCode`;
ALTER TABLE `houseinfo` MODIFY COLUMN `jibun` 			VARCHAR(10) AFTER `dong`;
ALTER TABLE `houseinfo` MODIFY COLUMN `roadName` 		VARCHAR(40) AFTER `jibun`;
ALTER TABLE `houseinfo` MODIFY COLUMN `roadNameBonbun`	VARCHAR(5) 	AFTER `roadName`;
ALTER TABLE `houseinfo` MODIFY COLUMN `apartmentName` 	VARCHAR(40) AFTER `roadNameBonbun`;
ALTER TABLE `houseinfo` MODIFY COLUMN `lat` 			VARCHAR(30) AFTER `buildYear`;

-- housedeal 컬럼 순서 변경
ALTER TABLE `housedeal` MODIFY COLUMN `aptCode`	BIGINT AFTER `no`;

/* ================ 테이블 추가 ================ */
-- member 테이블 추가
CREATE TABLE IF NOT EXISTS `members` (
    `userId` VARCHAR(16) NOT NULL PRIMARY KEY,
    `userPwd` VARCHAR(200) NOT NULL,
    `userName` VARCHAR(20) NOT NULL,
    `emailId` VARCHAR(20) NOT NULL,
    `emailDomain` VARCHAR(30) NOT NULL,
    `joinDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `grade` VARCHAR(20) not null default 'basic',
    `token` varchar(1000)
);

-- board 테이블 추가
CREATE TABLE IF NOT EXISTS `board` (
	`articleNo` 	INT 			AUTO_INCREMENT PRIMARY KEY,
	`userId` 		VARCHAR(16),
	`subject` 		VARCHAR(100),
	`content` 		VARCHAR(2000),
	`hit` 			INT 			DEFAULT 0,
	`boardType` 	VARCHAR(20) 	DEFAULT 'information',
	`registerTime` 	TIMESTAMP 		DEFAULT CURRENT_TIMESTAMP
);

-- comment 테이블 추가
CREATE TABLE IF NOT EXISTS `comment` (
	`commentNo` 	INT 			AUTO_INCREMENT PRIMARY KEY,
	`articleNo` 	INT 			NOT NULL,
	`userId` 		VARCHAR(16) 	NOT NULL,
	`content` 		VARCHAR(500) 	NOT NULL,
	`registerTime` 	TIMESTAMP 		DEFAULT CURRENT_TIMESTAMP,
	`like` 			INT 			DEFAULT 0
);

-- forsale 테이블 추가
CREATE TABLE IF NOT EXISTS `forsale` (
    `forsaleNo` INT 		AUTO_INCREMENT PRIMARY KEY,
    `aptCode` 	BIGINT 		NOT NULL,
    `userId` 	VARCHAR(16) NOT NULL,
    `price` 	VARCHAR(20) NOT NULL
);

-- imginfo 테이블 추가
CREATE TABLE IF NOT EXISTS `imginfo` (
    `imgInfoNo` 	INT 		AUTO_INCREMENT PRIMARY KEY,
    `forsaleNo` 	INT,
    `articleNo` 	INT,
    `saveFolder` 	VARCHAR(45) NOT NULL,
    `originalName` 	VARCHAR(50) NOT NULL,
    `saveName` 		VARCHAR(50) NOT NULL
);

-- favorite 테이블 추가
CREATE TABLE IF NOT EXISTS `favorite` (
    `favoriteNo` 	INT 		AUTO_INCREMENT PRIMARY KEY,
    `userId` 		VARCHAR(16) NOT NULL,
    `forsaleNo` 	INT 		NOT NULL
);

/* ================ 제약사항 추가 ================ */
-- houseinfo 유니크 키 제거
alter table houseinfo drop index `UNIQUE`;

-- board 테이블 제약사항 추가
alter table `board`
add foreign key (`userId`)
references members (`userId`)
on update no action
on delete cascade;

-- comment 테이블 제약사항 추가
alter table `comment`
add foreign key (`articleNo`)
references board (`articleNo`)
on update no action
on delete cascade;

alter table `comment`
add foreign key (`userId`)
references members (`userId`)
on update no action
on delete cascade;

-- forsale 테이블 제약사항 추가
alter table `forsale`
add foreign key (`aptCode`)
references houseinfo (`aptCode`)
on update no action
on delete cascade;

alter table `forsale`
add foreign key (`userId`)
references members (`userId`)
on update no action
on delete cascade;

-- favorite 테이블 제약사항 추가
alter table `favorite`
add foreign key (`userId`)
references members (`userId`)
on update no action
on delete cascade;

alter table `favorite`
add foreign key (`forsaleNo`)
references forsale (`forsaleNo`)
on update no action
on delete cascade;

-- imginfo 테이블 제약사항 추가
alter table `imginfo`
add constraint 
foreign key (`forsaleNo`)
references forsale (`forsaleNo`)
on update no action
on delete cascade;

alter table `imginfo`
add constraint
foreign key (`articleNo`)
references board (`articleNo`)
on update no action
on delete cascade;



