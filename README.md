# HotMapper
通用mapper快速开发框架，基于mybatis，目标是集成更多的开发工具，包括支付，权限。

## 注解介绍：一共有9个注解，所有注解包地址都为：com.xianguo.hotmapper.annotation
  ## @Table
      此注解打在你的实体类class上，值为实体类对应的表明，此注解是必须的，不加的话启动项目时可能报错。
  ## @AnalysisType
      此注解打在你的实体类class上，值为你实体类对应的字段风格，此注解不是必须的，如果HotMapper找不到此注解或默认没有风格，值类型为AnalysusTypeEnmu的枚举类型,
    此注解会帮你推断数据库字段名称
    对应的值有
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
  ## @OrderBy
      此注解打在你的实体类字段上，此注解不是必须的，此注解的值是一个OrderByEnmu
    类型的枚举
    对应的的值为
      /**
    	 * 正序
    	 */
    	ASC,
    	/**
    	 * 倒叙
    	 */
    	DESC;
  ## @HotTransient
      此注解打在你的实体类字段上，此注解不是必须的，此注解不需要值，打上此注解
    的字段会被HotMapper忽略，你可以在实体类和数据库无关的字段打上此注解。
  ## @Condition
      此注解打在你的实体类字段上，此注解不是必须的，此注解不需要值，HotMapper会把打上此注解的字段忽略掉但是会把此注解认为是一个条件，需要搭配@Field注解使用。
  ## @Relation
      此注解打在你的实体类字段上，此注解不是必须的，这是一个关系注解，HotMapper会在查询的时候把对应的关系数据注入到当前字段，此注解有三个值
      1.service：传入被关联表的ServiceImpl.class，传入的class必须继承HotServiceImpl。
      2.fk：当前表的外键字段名称(实体字段名称)
      3.pk：被关联表的主键名称(实体字段名称)

## 框架搭建教程
  ## DAO
    在你的Dao目录下新建一个Dao接口，此接口必须继承HotDao<T>,HotDao的T为当前dao对应的实体
    public interface testDao extends HotDao<bean> {

    }
  ## Service
    在你的service目录下新建一个Service接口，此接口必须继承HotService<T>,HotService的T为
    当前对应的实体
    public interface testService extends HotService<bean> {

    }
  ## ServiceImpl
    在你的service目录下新建一个ServiceImpl实现，此实现必须继承HotServiceImpl<T,D>,HotService的T为
    当前实现对应的实体,D为当前实现对应的Dao接口，并且实现getDao接口，getDao需要你自己创建当前实现对应
    的Dao，一般用Spring的Autowired

    @Service
    public class test extends HotServiceImpl<com.yrsoft.entity.test,aaaDao> {

    	@Autowired
    	aaaDao dao;

    	@Override
    	public aaaDao getDao() {
    		return dao;
    	}
    }
## 最后
  你已经介入了HotMapper可以愉快的调用你创建的ServiceImpl里面的所有增删改查方法啦，HotMapper
  也对PageHelper做了处理，可以完美支持，此框架希望能大幅度减少大家的重复性代码工作。
