DROP TABLE IF EXISTS `money_distribution`;

CREATE TABLE `money_distribution` (
                                      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                      `room_id` bigint(20) NOT NULL COMMENT 'room id',
                                      `user_id` bigint(20) DEFAULT NULL COMMENT 'user_id',
                                      `token` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT 'distribution token',
                                      `amount` int(20) NOT NULL COMMENT 'distribution total amount',
                                      `personnel` int(20) NOT NULL COMMENT 'distribution personnel',
                                      `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                                      `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
                                      PRIMARY KEY (`id`),
                                      KEY `id_idx` (`id`),
                                      KEY `room_id_idx` (`room_id`),
                                      KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'user id',
                        `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'user name',
                        `email` varchar(80) COLLATE utf8_unicode_ci NOT NULL COMMENT 'user email',
                        `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'password',
                        `profile_image` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'profile image',
                        `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                        `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
                        PRIMARY KEY (`id`),
                        KEY `id_idx` (`id`),
                        KEY `name_idx` (`name`),
                        KEY `email_idx` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `money_receive`;

CREATE TABLE `money_receive` (
                                 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                 `distribution_id` bigint(20) NOT NULL COMMENT 'distribution id',
                                 `room_id` bigint(20) NOT NULL COMMENT 'room id',
                                 `user_id` bigint(20) DEFAULT NULL COMMENT 'user_id',
                                 `token` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT 'distribution token',
                                 `amount` int(20) NOT NULL COMMENT 'distribution random amount',
                                 `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                                 `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
                                 PRIMARY KEY (`id`),
                                 KEY `id_idx` (`id`),
                                 KEY `distribution_id_idx` (`distribution_id`),
                                 KEY `room_id_idx` (`room_id`),
                                 KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `money_receive_request`;

CREATE TABLE `money_receive_request` (
                                         `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                         `distribution_id` bigint(20) NOT NULL COMMENT 'distribution id',
                                         `room_id` bigint(20) NOT NULL COMMENT 'room id',
                                         `user_id` bigint(20) DEFAULT NULL COMMENT 'user_id',
                                         `token` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT 'distribution token',
                                         `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                                         `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
                                         PRIMARY KEY (`id`),
                                         KEY `id_idx` (`id`),
                                         KEY `distribution_id_idx` (`distribution_id`),
                                         KEY `room_id_idx` (`room_id`),
                                         KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `room_user`;

CREATE TABLE `room_user` (
                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `room_id` bigint(20) NOT NULL COMMENT 'room id',
                             `user_id` bigint(20) NOT NULL COMMENT 'user id',
                             `is_participation` tinyint(1) NOT NULL DEFAULT 1 COMMENT '상태값 1: 참여, 0: 미참여',
                             `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                             `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
                             PRIMARY KEY (`id`),
                             KEY `id_idx` (`id`),
                             KEY `room_id_idx` (`room_id`),
                             KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

