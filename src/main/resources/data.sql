
-- 매장
MERGE INTO `store` (id, store_number, name) KEY(id) VALUES (1, 'S001', '판교점');
MERGE INTO `store` (id, store_number, name) KEY(id) VALUES (2, 'S002', '모란점');
MERGE INTO `store` (id, store_number, name) KEY(id) VALUES (3, 'S003', '야탑점');
MERGE INTO `store` (id, store_number, name) KEY(id) VALUES (4, 'S004', '정자점');

-- 사용자
MERGE INTO `user` (id, username, password, nickname, role, store_id) KEY(id) VALUES (1, '루크', '1234', '루크닉네임', 'ADMIN', null);
MERGE INTO `user` (id, username, password, nickname, role, store_id) KEY(id) VALUES (2, '소낙눈', '1234', '소낙눈닉네임', 'USER', null);
MERGE INTO `user` (id, username, password, nickname, role, store_id) KEY(id) VALUES (3, '포비', 'password123', '포비닉네임', 'USER', null);
MERGE INTO `user` (id, username, password, nickname, role, store_id) KEY(id) VALUES (4, '판교매니저', '1234', '판교매니저', 'MANAGER', 1);
MERGE INTO `user` (id, username, password, nickname, role, store_id) KEY(id) VALUES (5, '모란매니저', '1234', '모란매니저', 'MANAGER', 2);
MERGE INTO `user` (id, username, password, nickname, role, store_id) KEY(id) VALUES (6, '야탑매니저', '1234', '야탑매니저', 'MANAGER', 3);
MERGE INTO `user` (id, username, password, nickname, role, store_id) KEY(id) VALUES (7, '정자매니저', '1234', '정자매니저', 'MANAGER', 4);

-- 테마 (판교점: 공포/로맨스, 모란점: 감성, 야탑점: 스릴러, 정자점: 스토리)
MERGE INTO theme (id, store_id, name, description, image_url, required_time) KEY(id) VALUES (1, 1, '공포', '등골이 오싹한 공포 테마', 'https://i.pinimg.com/736x/b3/4a/d4/b34ad4fd5bcbced41d7f340c539cd4d7.jpg', '02:00:00');
MERGE INTO theme (id, store_id, name, description, image_url, required_time) KEY(id) VALUES (2, 1, '로맨스', '두근두근 로맨스 테마', 'https://i.pinimg.com/1200x/c2/a3/00/c2a30020e8c1a25f7032d7d360886de7.jpg', '02:00:00');
MERGE INTO theme (id, store_id, name, description, image_url, required_time) KEY(id) VALUES (3, 2, '감성', '눈물 쏙 빼는 감성 테마', 'https://i.pinimg.com/736x/af/d0/9c/afd09c1c478137db8da6c29d610cc693.jpg', '02:00:00');
MERGE INTO theme (id, store_id, name, description, image_url, required_time) KEY(id) VALUES (4, 3, '스릴러', '심장이 쫄깃한 스릴러 테마', 'https://i.pinimg.com/736x/be/c9/d6/bec9d6c70028d43f8a4c00330c51c516.jpg', '02:00:00');
MERGE INTO theme (id, store_id, name, description, image_url, required_time) KEY(id) VALUES (5, 4, '스토리', '탄탄한 세계관의 스토리 테마', 'https://i.pinimg.com/1200x/78/85/b7/7885b7216437601598a21bca10751bec.jpg', '02:00:00');

-- 스케줄 (오늘)
MERGE INTO schedule KEY(id) VALUES (1001, 1, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 12:00:00'));
MERGE INTO schedule KEY(id) VALUES (1002, 1, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 13:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 15:00:00'));
MERGE INTO schedule KEY(id) VALUES (1003, 1, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 16:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 18:00:00'));

MERGE INTO schedule KEY(id) VALUES (1004, 2, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 12:00:00'));
MERGE INTO schedule KEY(id) VALUES (1005, 2, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 14:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 16:00:00'));
MERGE INTO schedule KEY(id) VALUES (1006, 2, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 18:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 20:00:00'));

MERGE INTO schedule KEY(id) VALUES (1007, 3, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 11:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 13:00:00'));
MERGE INTO schedule KEY(id) VALUES (1008, 3, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 15:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 17:00:00'));
MERGE INTO schedule KEY(id) VALUES (1009, 3, FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 19:00:00'), FORMATDATETIME(CURRENT_DATE, 'yyyy-MM-dd 21:00:00'));

-- 스케줄 (내일)
MERGE INTO schedule KEY(id) VALUES (1010, 4, FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 12:00:00'));
MERGE INTO schedule KEY(id) VALUES (1011, 4, FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 13:00:00'), FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 15:00:00'));
MERGE INTO schedule KEY(id) VALUES (1012, 4, FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 16:00:00'), FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 18:00:00'));

MERGE INTO schedule KEY(id) VALUES (1013, 5, FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 12:00:00'));
MERGE INTO schedule KEY(id) VALUES (1014, 5, FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 14:00:00'), FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 16:00:00'));
MERGE INTO schedule KEY(id) VALUES (1015, 5, FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 18:00:00'), FORMATDATETIME(DATEADD(DAY, 1, CURRENT_DATE), 'yyyy-MM-dd 20:00:00'));

-- 스케줄 (과거 - 인기 테마 통계용)
MERGE INTO schedule KEY(id) VALUES (1, 1, FORMATDATETIME(DATEADD(DAY, -1, CURRENT_DATE), 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(DATEADD(DAY, -1, CURRENT_DATE), 'yyyy-MM-dd 12:00:00'));
MERGE INTO schedule KEY(id) VALUES (2, 1, FORMATDATETIME(DATEADD(DAY, -2, CURRENT_DATE), 'yyyy-MM-dd 13:00:00'), FORMATDATETIME(DATEADD(DAY, -2, CURRENT_DATE), 'yyyy-MM-dd 15:00:00'));
MERGE INTO schedule KEY(id) VALUES (3, 1, FORMATDATETIME(DATEADD(DAY, -3, CURRENT_DATE), 'yyyy-MM-dd 16:00:00'), FORMATDATETIME(DATEADD(DAY, -3, CURRENT_DATE), 'yyyy-MM-dd 18:00:00'));
MERGE INTO schedule KEY(id) VALUES (4, 1, FORMATDATETIME(DATEADD(DAY, -4, CURRENT_DATE), 'yyyy-MM-dd 19:00:00'), FORMATDATETIME(DATEADD(DAY, -4, CURRENT_DATE), 'yyyy-MM-dd 21:00:00'));

MERGE INTO schedule KEY(id) VALUES (5, 2, FORMATDATETIME(DATEADD(DAY, -1, CURRENT_DATE), 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(DATEADD(DAY, -1, CURRENT_DATE), 'yyyy-MM-dd 12:00:00'));
MERGE INTO schedule KEY(id) VALUES (6, 2, FORMATDATETIME(DATEADD(DAY, -2, CURRENT_DATE), 'yyyy-MM-dd 13:00:00'), FORMATDATETIME(DATEADD(DAY, -2, CURRENT_DATE), 'yyyy-MM-dd 15:00:00'));
MERGE INTO schedule KEY(id) VALUES (7, 2, FORMATDATETIME(DATEADD(DAY, -3, CURRENT_DATE), 'yyyy-MM-dd 16:00:00'), FORMATDATETIME(DATEADD(DAY, -3, CURRENT_DATE), 'yyyy-MM-dd 18:00:00'));

MERGE INTO schedule KEY(id) VALUES (8, 3, FORMATDATETIME(DATEADD(DAY, -1, CURRENT_DATE), 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(DATEADD(DAY, -1, CURRENT_DATE), 'yyyy-MM-dd 12:00:00'));
MERGE INTO schedule KEY(id) VALUES (9, 3, FORMATDATETIME(DATEADD(DAY, -5, CURRENT_DATE), 'yyyy-MM-dd 13:00:00'), FORMATDATETIME(DATEADD(DAY, -5, CURRENT_DATE), 'yyyy-MM-dd 15:00:00'));

MERGE INTO schedule KEY(id) VALUES (10, 4, FORMATDATETIME(DATEADD(DAY, -2, CURRENT_DATE), 'yyyy-MM-dd 16:00:00'), FORMATDATETIME(DATEADD(DAY, -2, CURRENT_DATE), 'yyyy-MM-dd 18:00:00'));

MERGE INTO schedule KEY(id) VALUES (11, 5, FORMATDATETIME(DATEADD(DAY, -10, CURRENT_DATE), 'yyyy-MM-dd 10:00:00'), FORMATDATETIME(DATEADD(DAY, -10, CURRENT_DATE), 'yyyy-MM-dd 12:00:00'));

-- 예약
MERGE INTO reservation KEY(id) VALUES (1, 1, 1);
MERGE INTO reservation KEY(id) VALUES (2, 2, 2);
MERGE INTO reservation KEY(id) VALUES (3, 3, 3);
MERGE INTO reservation KEY(id) VALUES (4, 1, 4);

MERGE INTO reservation KEY(id) VALUES (5, 2, 5);
MERGE INTO reservation KEY(id) VALUES (6, 3, 6);
MERGE INTO reservation KEY(id) VALUES (7, 1, 7);

MERGE INTO reservation KEY(id) VALUES (8, 2, 8);
MERGE INTO reservation KEY(id) VALUES (9, 3, 9);

MERGE INTO reservation KEY(id) VALUES (10, 1, 10);

MERGE INTO reservation KEY(id) VALUES (11, 2, 11);
