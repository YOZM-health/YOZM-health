create database yozm_health;
use yozm_health;

CREATE TABLE `USER` (
                        `USER_NO` BIGINT AUTO_INCREMENT NOT NULL COMMENT '사용자 번호',
                        `USER_ID` VARCHAR(50) NOT NULL COMMENT '사용자 아이디',
                        `USER_PW` VARCHAR(250) NOT NULL COMMENT '사용자 비밀번호',
                        `USER_EMAIL` VARCHAR(50) NULL COMMENT '사용자 이메일주소',
                        `USER_NAME` VARCHAR(50) NOT NULL COMMENT '사용자 이름',
                        `USER_BIRTH` VARCHAR(100) NOT NULL COMMENT '사용자 생년월일',
                        `USER_TEL` VARCHAR(100) NOT NULL COMMENT '사용자 전화번호',
                        `USER_ADDR` VARCHAR(300) NULL COMMENT '사용자 주소',
                        `USER_NICKNAME` VARCHAR(50) NOT NULL COMMENT '사용자 닉네임',
                        `ENROLL_DATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '사용자 가입일',
                        `PROFILE_IMG` VARCHAR(300) NULL COMMENT '프로필 이미지 저장 경로',
                        `USER_DEL_FL` CHAR(1) NOT NULL DEFAULT 'N' COMMENT '탈퇴여부(N:탈퇴X, Y:탈퇴O)',
                        `USER_TYPE` INT NOT NULL DEFAULT 1 COMMENT '회원 유형 (1:일반, 2:트레이너, 3:관리자)',
                        `USER_CENTER` VARCHAR(1000) NULL COMMENT '코치회원 활동센터',
                        PRIMARY KEY (`USER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `FOLLOWS` (
                           `FOLLOW_ID` BIGINT NOT NULL,
                           `FROM_USER` BIGINT NOT NULL COMMENT '팔로우',
                           `TO_USER` BIGINT NOT NULL COMMENT '요청받은자',
                           PRIMARY KEY (`FOLLOW_ID`)
);

CREATE TABLE `BOARD` (
                         `BOARD_NO` INT auto_increment NOT NULL COMMENT '게시글 번호',
                         `BOARD_TITLE` VARCHAR(200) NOT NULL COMMENT '게시글 제목',
                         `BOARD_CONTENT` TEXT NOT NULL COMMENT '게시글 내용',
                         `BOARD_AUTHOR` VARCHAR(200) NOT NULL COMMENT '게시글 작성자',
                         `CREATE_DATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
                         `READ_COUNT` INT NOT NULL DEFAULT 0 COMMENT '조회수',
                         `BOARD_DEL_FL` CHAR(1) NULL DEFAULT 'N' COMMENT '게시글 상태',
                         `USER_NO` INT NOT NULL COMMENT '사용자 번호',
                         `BOARD_CODE` INT NOT NULL COMMENT '게시판 코드',
                         PRIMARY KEY (`BOARD_NO`)
);
# 게시글 임시글 저장기능을 위해서 추가적으로 컬럼을 추가
ALTER TABLE BOARD ADD COLUMN BOARD_STATUS CHAR(1) DEFAULT 'P' COMMENT '게시글 상태 (P: 작성중, C: 완료)';

CREATE TABLE `CHATTING` (
                            `CHAT_NO` BIGINT NOT NULL COMMENT '채팅 메시지 번호',
                            `CHAT_CONTENT` VARCHAR(1500) NOT NULL COMMENT '채팅 내용',
                            `SEND_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '채팅 전송 시간',
                            `READ_ST_FL` CHAR(1) NOT NULL DEFAULT 'N' COMMENT "읽음여부('N':안읽음 / 'Y':읽음)",
                            `CHATROOM_NO` INT NOT NULL COMMENT '1:1 상담방(채팅방) 번호',
                            `USER_NO` INT NOT NULL COMMENT '사용자 번호',
                            PRIMARY KEY (`CHAT_NO`)
);

CREATE TABLE `REPLY` (
                         `REPLY_NO` INT NOT NULL COMMENT '댓글 번호(기본키)',
                         `BOARD_NO2` INT NOT NULL COMMENT '게시글 번호',
                         `REPLY_AUTHOR` VARCHAR(50) NOT NULL COMMENT '댓글 작성자',
                         `REPLY_CONTENTS` VARCHAR(255) NOT NULL COMMENT '댓글 내용',
                         `PARENT_REPLY_NO` INT NULL COMMENT '대댓글 번호',
                         `USER_NO` INT NOT NULL COMMENT '회원번호(외래키)',
                         `REPLY_CREATED_TIME` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 작성일',
                         `REPLY_UPDATED_TIME` DATETIME DEFAULT NULL COMMENT '댓글 수정일',
                         PRIMARY KEY (`REPLY_NO`, `BOARD_NO2`),
                         FOREIGN KEY (`BOARD_NO2`) REFERENCES `BOARD` (`BOARD_NO`)
);

CREATE TABLE `CHATROOM` (
                            `CHATROOM_NO` INT NOT NULL COMMENT '1:1 상담방(채팅방) 번호',
                            `CHATROOM_ST_FL` CHAR(1) NOT NULL DEFAULT 'N',
                            `SENDER_NO` INT NOT NULL COMMENT '메시지를 보낸 사람',
                            `RECEIVER_NO` INT NOT NULL COMMENT '메시지를 받은 회원',
                            PRIMARY KEY (`CHATROOM_NO`)
);

CREATE TABLE `BOARD_LIKE` (
                              `BOARD_NO2` INT NOT NULL COMMENT '게시글 번호',
                              `USER_NO2` INT NOT NULL COMMENT '사용자 번호',
                              PRIMARY KEY (`BOARD_NO2`, `USER_NO2`),
                              FOREIGN KEY (`BOARD_NO2`) REFERENCES `BOARD` (`BOARD_NO`),
                              FOREIGN KEY (`USER_NO2`) REFERENCES `USER` (`USER_NO`)
);

CREATE TABLE `COACH_BOARD` (
                               `BOARD_NO` INT NOT NULL COMMENT '게시글 번호',
                               `SUB_TITLE` VARCHAR(500) NOT NULL COMMENT '부제목',
                               `EDUCATION` VARCHAR(2500) NULL COMMENT '학력',
                               `CAREER` VARCHAR(2500) NULL COMMENT '경력',
                               `AWARD` VARCHAR(2500) NULL COMMENT '상이력',
                               PRIMARY KEY (`BOARD_NO`),
                               FOREIGN KEY (`BOARD_NO`) REFERENCES `BOARD` (`BOARD_NO`)
);

CREATE TABLE `CATEGORY` (
                            `CAT_CODE` INT NOT NULL COMMENT '카테고리 코드',
                            `CAT_NAME` VARCHAR(60) NOT NULL COMMENT '카테고리 내용',
                            `BOARD_NO` INT NOT NULL COMMENT '게시글 번호',
                            PRIMARY KEY (`CAT_CODE`)
);

CREATE TABLE `BOARD_TYPE` (
                              `BOARD_CODE` INT NOT NULL COMMENT '게시판 코드',
                              `BOARD_NAME` VARCHAR(30) NOT NULL COMMENT '게시판 이름',
                              PRIMARY KEY (`BOARD_CODE`)
);

CREATE TABLE `REPLY_LIKE` (
                              `USER_NO2` bigint NOT NULL COMMENT '사용자 번호',
                              `REPLY_NO2` INT NOT NULL COMMENT '댓글 번호(기본키)',
                              PRIMARY KEY (`USER_NO2`, `REPLY_NO2`),
                              FOREIGN KEY (`USER_NO2`) REFERENCES `USER` (`USER_NO`),
                              FOREIGN KEY (`REPLY_NO2`) REFERENCES `REPLY` (`REPLY_NO`)
);

CREATE TABLE `BOARD_ATTACH` (
                                `ATTACH_NO` INT NOT NULL COMMENT '첨부파일 번호',
                                `BOARD_NO2` INT NOT NULL COMMENT '게시글 번호',
                                `ORIGIN_NAME` VARCHAR(255) NOT NULL COMMENT '원본파일명',
                                `STORE_NAME` VARCHAR(255) NOT NULL COMMENT '저장된 파일명',
                                `FILE_SIZE` INT NOT NULL COMMENT '파일의 크기',
                                `FILE_PATH` VARCHAR(255) NOT NULL COMMENT '파일의 경로',
                                `FILE_CREATED_DATE` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업로드 한 날짜',
                                PRIMARY KEY (`ATTACH_NO`, `BOARD_NO2`),
                                FOREIGN KEY (`BOARD_NO2`) REFERENCES `BOARD` (`BOARD_NO`)
);
