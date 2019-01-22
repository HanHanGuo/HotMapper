# HotMapper
通用mapper快速开发框架，基于mybatis，目标是集成更多的开发工具，包括支付，权限。

   # 注解介绍：
    一共有9个注解，所有注解包地址都为：com.xianguo.hotmapper.annotation
   ## @Table
        此注解打在你的实体类class上，值为实体类对应的表明，此注解是必须的，不加的话启动项目时可能报错。
   ## @AnalysisType
        此注解打在你的实体类class上，值为你实体类对应的字段风格，此注解不是必须的，如果HotMapper找不到此注解或默认没有风格，值类型为AnalysusTypeEnmu的枚举类型,
      此注解会帮你推断数据库字段名称
      对应的值有
```Java
      /**
       * 首字母大写驼峰
       */
      INITIALS_UP_HUMP,
      /**
       * 首字大写下划线
       */
      INITIALS_UP_UNDERLINE,
      /**
       * 驼峰
       */
      HUMP,
      /**
       * 下划线
       */
      UNDERLINE,
      /**
       * 全大写下划线
       */
      UP_UNDERLINE,
      /**
       * 首字母大写单词大写下划线
       */
      INITIALS_UP_WORD_UP_UNDERLINE,
      /**
       * 单词大写下划线
       */
      WORD_UP_UNDERLINE,
      /**
       * 没有风格
       */
      NONE;
```
   ## @Id
        此注解打在你的实体类字段上，HotMapper会把打上此注解的字段认为id，此注解不是必须的，如果找不到此注解HotMapper会自动推断你的id,
      推断方式为（把实体类字段名称转为小写，在判断是否等于id）.
   ## @Field
        此注解打在你的实体类字段上，此注解不是必须的，值为实体字段所对应的数据库字段名称，如果HotMapper找不到此注解会以@AnalysisType所指明的风格来
     推断
   ## @Symbol
        此注解打在你的实体类字段上，此注解不是必须的，值为查询表时该字段的条件操作符，值是一个SymbolEnmu的枚举类型
        如果注解值为LIKE，HotMapper会在查询，更新，删除时在字段值两边加上%。
        对应的值有
```Java
        /**
         * 等于
         */
        EQUAL,
        /**
         * 模糊
         */
        LIKE,
        /**
         * 大于
         */
        GREATER,
        /**
         * 小于
         */
        LESS,
        /**
         * 大于等于
         */
        GREATER_EQUAL,
        /**
         * 小于等于
         */
        LESS_EQUAL;
```
