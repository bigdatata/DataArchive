
/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`database_archive` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `database_archive`;

/*Table structure for table `archive_task` */

DROP TABLE IF EXISTS `archive_task`;

CREATE TABLE `archive_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source_database` varchar(128) NOT NULL COMMENT '源数据库',
  `source_table` varchar(128) NOT NULL COMMENT '源数据库表',
  `target_database` varchar(128) NOT NULL COMMENT '目标数据库',
  `target_table` varchar(128) NOT NULL COMMENT '目标数据库表',
  `archive_type` tinyint(4) NOT NULL COMMENT '归档类型',
  `key_column` varchar(128) NOT NULL COMMENT '主键列',
  `day_number` int(11) NOT NULL COMMENT '归档多少天以前的数据',
  `date_column` varchar(128) NOT NULL COMMENT '比较的时间列',
  `archive_columns` varchar(1024) NOT NULL COMMENT '所有归档的列',
  `condition_sql` varchar(1024) DEFAULT NULL COMMENT '归档sql条件',
  `per_save_num` int(11) DEFAULT NULL COMMENT '每次保存数量',
  `per_delete_num` int(11) DEFAULT NULL COMMENT '每次删除数量',
  `cron_expression` varchar(128) DEFAULT NULL COMMENT '归档执行时间cronExpression表达式',
  `is_enable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已启动任务',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `created` datetime NOT NULL COMMENT '创建时间',
  `updated` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='归档任务';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
