# Introduction #

this page is guide for developers.


# Details #
details index.
  * setting up sonic.jar
  * create "Action" class
  * create "Service" class (​u​n​d​e​r​ ​c​o​n​s​t​r​u​c​t​i​o​n​.)

### setting up sonic.jar ###
  * download sonic.zip from this page http://code.google.com/p/sonic-project/downloads/list
  * **setting your GAE project on eclipse.
    1. unzip sonic.zip
    1. put sonic.jar to directory that is "your-gae-project/war/WEB-INF/lib"
    1. setting classpath your-gae-project. sonic.jar and dependencies(sonic.zip includes dependencies)
    1. edit web.xml(add this servlet-mapping.)
```
	<servlet>
		<servlet-name>dispatcher</servlet-name>
		<servlet-class>com.googlecode.sonic.servlet.DispatcherServlet</servlet-class>
		<init-param>
      		    <param-name>actionPackage</param-name>
      		    <param-value>your.apps.actionpackage</param-value>
    	        </init-param>
	</servlet>	
	<servlet-mapping>
		<servlet-name>dispatcher</servlet-name>
		<url-pattern>*.gae</url-pattern>
	</servlet-mapping>
```**


### create "Action" class ###
  1. create Action package.
> > e.g.) your.apps.actionpackage
  1. create Action class.
> > Action class needs extends "com.googlecode.sonic.action.ActionSupport"
```
   package your.apps.actionpackage;

   import com.googlecode.sonic.action.ActionSupport;

   public class IndexAction extends ActionSupport {
       private static final long serialVersionUID = 321255017934978886L;
   }
```
  1. create public method has no argument.
```
   package your.apps.actionpackage;

   import com.googlecode.sonic.action.ActionSupport;
   import com.googlecode.sonic.constant.ActionResult;

   public class IndexAction extends ActionSupport {
       private static final long serialVersionUID = 321255017934978886L;

       public ActionResult index() throws Exception {
           return SUCCESS;
       }
   }
```
  1. add @RequestMapping annotation to public method.
```
   package your.apps.actionpackage;

   import com.googlecode.sonic.action.ActionSupport;
   import com.googlecode.sonic.constant.ActionResult;
   import com.googlecode.sonic.annotation.RequestMapping;
   import com.googlecode.sonic.annotation.Result;

   public class IndexAction extends ActionSupport {
       private static final long serialVersionUID = 321255017934978886L;

       @RequestMapping(to = "/index.gae", 
           results = { @Result(code = SUCCESS, to = "/WEB-INF/jsp/index.jsp") })
       public ActionResult index() throws Exception {
           return SUCCESS;
       }
   }
```
  1. get HttpServletRequest and print "message" on index.jsp.
```
   package your.apps.actionpackage;

   import com.googlecode.sonic.action.ActionSupport;
   import com.googlecode.sonic.constant.ActionResult;
   import com.googlecode.sonic.annotation.RequestMapping;
   import com.googlecode.sonic.annotation.Result;

   public class IndexAction extends ActionSupport {
       private static final long serialVersionUID = 321255017934978886L;

       @RequestMapping(to = "/index.gae", 
           results = { @Result(code = SUCCESS, to = "/WEB-INF/jsp/index.jsp") })
       public ActionResult index() throws Exception {
           HttpServletRequest request = getRequest();
	   request.setAttribute("message", getMessage());
	   return SUCCESS;
       }
 
       private String getMessage() {
           return new StringBuilder()
                      .append("Hello,").append("Sonic Project.")
		      .toString();
	}
   }
```
  1. run jetty, and request to [http://localhost:8888/index.gae ](.md)
> > It is surely displayed on your browser "Hello,Sonic Project. ".

### create "Service" class (​u​n​d​e​r​ ​c​o​n​s​t​r​u​c​t​i​o​n​.) ###
  1. create dto class.
> > XXXDto class needs extends DefaultDto.
```
   public class BlogEntryDto extends DefaultDto {

	private static final long serialVersionUID = -6315647904607823576L;

	// entry title
	private String title;
	// main text
	private Text text;
	// tag1
	private String tag1;
	// tag2
	private String tag2;
	// tag3
	private String tag3;
	// comments
	private List<BlogCommentDto> blogCommentDtoList;
	// comment count.
	private int commentCount;
        
        // getter, setter
```
  1. define Bigtables's kind in enum class.
```
    public enum EntityKindNames implements KindNames {

	BlogEntry("BlogEntry"), //
	BlogComment("BlogCommnet");

	private String name;

	private EntityKindNames(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
```
  1. create Service class.
> > service class needs extends GenericService class.
```
   public class BlogEntryService extends GenericService<BlogEntryDto> {

	public BlogEntryService() {
		kindOfEntity = EntityKindNames.BlogEntry.getName();
	}
```
  1. add "get" method to Service class.
> > Only this processing, you can get "Entity" kind name is "BlogEntry"
```
  public class BlogEntryService extends GenericService<BlogEntryDto> {

     public BlogEntryService() {
         kindOfEntity = EntityKindNames.BlogEntry.getName();
     }

     public BlogEntryDto get(Long id) throws ServiceException {
         AssertionUtil.assertIsValid(ID, id);
         try {
             return entityToDto(super.select(id));
         } catch (Exception e) {
             throw new ServiceException(e);
     }
     
     @Override
     protected BlogEntryDto entityToDto(Entity entity) 
         throws ServiceException {
         BlogEntryDto dto = new BlogEntryDto();
         if (entity == null)
             return dto;
         try {
             ExBeanUtil.copyProperties(entity, dto);
             return dto;
         } catch (Exception e) {
             throw new ServiceException(e);
         }
     }
  }
```
  1. add "register" method to Service class.
```
  public class BlogEntryService extends GenericService<BlogEntryDto> {

     public BlogEntryService() {
         kindOfEntity = EntityKindNames.BlogEntry.getName();
     }

     public BlogEntryDto get(Long id) throws ServiceException {
         AssertionUtil.assertIsValid(ID, id);
         try {
             return entityToDto(super.select(id));
         } catch (Exception e) {
             throw new ServiceException(e);
         }
     }

     public Key register(BlogEntryDto dto) {
         AssertionUtil.assertIsValid(
            BlogEntryDto.class.getCanonicalName(), dto);
         Entity entity = createEntityByKey(getNextId());
         entity.setProperty(TITLE, dto.getTitle());
         entity.setProperty(TEXT, dto.getText());
         entity.setProperty(TAG1, dto.getTag1());
         entity.setProperty(TAG2, dto.getTag2());
         entity.setProperty(TAG3, dto.getTag3());
         return datastoreService.put(entity);
     }

     @Override
     protected BlogEntryDto entityToDto(Entity entity) 
         throws ServiceException {
         BlogEntryDto dto = new BlogEntryDto();
         if (entity == null)
             return dto;
         try {
             ExBeanUtil.copyProperties(entity, dto);
             return dto;
         } catch (Exception e) {
             throw new ServiceException(e);
         }
     }
  }
```
  1. The other basic functions of Service class.
> > these methods are defined com.googlecode.sonic.service.GenericService class.
    * Entity selectForcibly(Long id)
    * List< Entity > selectAll()
    * int count()
    * void physicalDelete(Long id)
    * Key update(Entity entity)
    * Key logicalDelete(Long id)
    * void truncate()
    * long getNextId()
