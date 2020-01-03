# HotMapper
通用mapper快速开发框架，基于mybatis，目标是集成更多的开发工具，包括支付，权限。

## 注解介绍：一共有10个注解，所有注解包地址都为：com.xianguo.hotmapper.annotation
  ## @Table
      此注解打在你的实体类class上，值为实体类对应的表名，此注解是必须的，不加的话启动项目时可能报错。
  ## @AnalysisType
      此注解打在你的实体类class上，值为你实体类对应的字段风格，此注解不是必须的，如果HotMapper找不到此注解或默认没有风
      格，值类型为AnalysusTypeEnmu的枚举类型,此注解会帮你推断数据库字段名称
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
      此注解打在你的实体类字段上，HotMapper会把打上此注解的字段认为id，此注解不是必须的，如果找不到此注解HotMapper会自动
      推断你的id,
    推断方式为（把实体类字段名称转为小写，在判断是否等于id）.
  ## @Field
      此注解打在你的实体类字段上，此注解不是必须的，值为实体字段所对应的数据库字段名称，如果HotMapper找不到此注解会
      以@AnalysisType所指明的风格来推断
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
      此注解打在你的实体类字段上，此注解不是必须的，此注解不需要值，HotMapper会把打上此注解的字段忽略掉
      但是会把此注解认为是一个条件，需要搭配@Field注解使用。
  ## @Relation
      此注解打在你的实体类字段上，此注解不是必须的，这是一个关系注解，HotMapper会在查询的时候把对应的关系
      数据注入到当前字段，此注解有三个值
      1.service：传入被关联表的ServiceImpl.class，传入的class必须继承HotServiceImpl。
      2.fk：当前表的外键字段名称(实体字段名称)
      3.pk：被关联表的主键名称(实体字段名称)
  ## @NoCache
      此注解打在你的实体类字段上，此注解不是必须的，此注解不需要值，HotMapper有一个会话缓存，作用域非常小，
      如果你有两个实体有互相引用的关系，请给互相引用的字段打上此注解，不然会出现无限递归，造成JSON转换时出现
      堆栈溢出。
## 框架搭建教程

  ## Bean
  ```Java
    @Table("STUDENT")//实体对应的表名
    @ReTable//逆向工程，加上次注解可以在启动项目时自己建立STUDENT表
    @AnalysisType(AnalysusTypeEnmu.UP_UNDERLINE)//数据库字段风格(这里表示全大写加下划线,HotMapper会自己转换)
    public class Student {

      @FieldInfo(length="32",isNull=FieldIsNull.NOT_NULL,type=FieldType.VARCHAR,detail="学生id")//字段信息 加了@ReTable之后用到 数据库对应的字段信息
      private String id;

      @FieldInfo(length="32")
      private String name;

      @FieldInfo(length="3")
      private String age;

      @FieldInfo(length="1")
      private String sex;

      private String classRoomId;//教室Id

      @OrderBy(OrderByEnmu.DESC)//查询时根据创建日期倒叙
      private String createDate;

      @Relation(fk="classRoomId",pk="id",service=StudentServiceImpl.class)//自动关联关系，配置好了之后HotMapper会在查询时注入对应的值，fk当实体外键，pk被关联实体主键，service被关联实体对应的ServiceImpl必须继承HotServiceImpl
      private Student student;
   }
  ```
  ```Java
  @Table("CLASS_ROOM")
  @AnalysisType(AnalysusTypeEnmu.UP_UNDERLINE)
  public class ClassRoom {
    @Id
    private String id;

    private String name;

    @OrderBy(OrderByEnmu.DESC)
    private String createDate;
  }
  ```
  
  
  ## DAO
    在你的Dao目录下新建一个Dao接口，此接口必须继承HotDao<T>,HotDao的T为当前dao对应的实体
  ```Java
    public interface TestDao extends HotDao<Bean> {

    }
  ```
  ## Service
    在你的service目录下新建一个Service接口，此接口必须继承HotService<T>,HotService的T为
    当前对应的实体
  ```Java
    public interface TestService extends HotService<Bean> {

    }
  ```
  ## ServiceImpl
    在你的service目录下新建一个ServiceImpl实现，此实现必须继承HotServiceImpl<T,D>,HotService的T为
    当前实现对应的实体,D为当前实现对应的Dao接口，并且实现getDao接口，getDao需要你自己创建当前实现对应
    的Dao，一般用Spring的Autowired,并接入你创建的对应Service接口
  ```Java
    @Service
    public class TestServiceImpl extends HotServiceImpl<Bean,TestDao> implements TestService {

    	@Autowired
    	TestDao dao;

    	@Override
    	public TestDao getDao() {
    		return dao;
    	}
    }
 ```
   ## 使用
 ```Java
    studentService.delete(Sutdent sutdent);//根据实体条件删除
    studentService.deleteById(String id);//根据id删除
    studentService.deleteByIds(List<String> ids);//根据id集合删除多条
    studentService.save(List<Student> students);//根据实体集合保存多条
    studentService.save(Sutdent sutdent);//根据实体保存一条
    studentService.update(Student student);//根据实体更新一条
    studentService.update(List<Student> students);//根据实体更新多条
    Student student = studentService.select(Sutdent sutdent);//根据条件查询一条
    Student student = studentService.select(Sutdent sutdent, true);//根据条件查询一条并且处理一层关联关系
    Student student = studentService.select(Sutdent sutdent, Integer 3);//根据条件查询一条并且处理3层关联关系
    Student student = studentService.selectById(String id);//根据id查询一条
    Student student = studentService.selectById(String id, true);//根据条件查询一条并且处理一层关联关系
    Student student = studentService.selectById(String id, 3);//根据条件查询一条并且处理3层关联关系
    List<Student> students = studentService.selectList(Sutdent sutdent);//根据条件查询多条
    List<Student> students = studentService.selectList(Sutdent sutdent, true);//根据条件查询多条并且处理一层关联关系
    List<Student> students = studentService.selectList(Sutdent sutdent, Integer 3);//根据条件查询多条并且处理3层关联关系
 ```

## 最后
  你已经接入了HotMapper可以愉快的调用你创建的ServiceImpl里面的所有增删改查方法啦，HotMapper
  也对PageHelper做了处理，可以完美支持，此框架希望能大幅度减少大家的重复性代码工作。
