package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Blog;
import utility.ConnectionManager;



public class BlogDaoImpl implements BlogDaoInterface {

	

	private static final String SELECT_BLOG_BY_ID = "select blogId,blogname,blogDiscription,postedOn from blog where blogId =?";
	
	private static final String DELETE_BLOG_BY_ID = "delete from blog where blogId = ?";
	private static final String UPDATE_BLOG = "update blog set  blogName = ?, blogDiscription = ?, postedOn = ? where blogId = ?";

	public BlogDaoImpl() {
	}

	@Override
	public void insertBlog(Blog blog) throws SQLException 
	{
		// TODO Auto-generated method stub
		Connection con=ConnectionManager.getConnection();
		//if(con!=null)
			//System.out.println("Established");
		String sql="INSERT INTO BLOG(blogId,blogName ,blogDiscription,postedOn)VALUES(?,?,?,?)";
		PreparedStatement st=null;
		
			st = con.prepareStatement(sql);
		
		
		
			st.setLong(1, blog.getBlogId());
		
		
			st.setString(2,blog.getBlogTitle());
		
			st.setString(3,blog.getBlogDescription());
		
			st.setDate(4, java.sql.Date.valueOf(blog.getPostedOn()));
		
			st.executeUpdate();
		
			con.close();
		
		
	}

	
	

	@Override
	public Blog selectBlog(int blogId) {
		Blog blog = null;
		System.out.println(blogId);
		// Step 1: Establishing a Connection
		try (Connection connection = ConnectionManager.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BLOG_BY_ID)) {
			preparedStatement.setInt(1, blogId);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int Id = rs.getInt("blogId");
				String blogTitle = rs.getString("blogname");
				String blogDescription = rs.getString("blogDiscription");
				LocalDate postedOn = rs.getDate("postedOn").toLocalDate();
				
				blog = new Blog();
				blog.setBlogId(Id);
				blog.setBlogTitle(blogTitle);
				blog.setBlogDescription(blogDescription);
				blog.setPostedOn(postedOn);
				
			}
		} catch (SQLException exception) {
			System.out.println(exception);
		}
		return blog;
	}

	@Override
	public List<Blog> selectAllBlogs() {
		Blog blog = null;
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Blog> blogList = new ArrayList<>();

		// Step 1: Establishing a Connection
		try (Connection connection = ConnectionManager.getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement("select * from BLOG") ) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int Id = rs.getInt("blogId");
				String blogTitle = rs.getString("blogname");
				
				String blogDescription = rs.getString("blogDiscription");
				LocalDate postedOn = rs.getDate("postedOn").toLocalDate();
				
				blog = new Blog();
				blog.setBlogId(Id);
				blog.setBlogTitle(blogTitle);
				blog.setBlogDescription(blogDescription);
				blog.setPostedOn(postedOn);
				blogList.add(blog);
			}
		} catch (SQLException exception) {
			System.out.println(exception);
		}
		return blogList;
	}
	@Override
	public boolean deleteBlog(int id) throws SQLException {
		System.out.println(id);
		boolean rowDeleted;
		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_BLOG_BY_ID)) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	@Override
	public boolean updateBlog(Blog blog) throws Exception {
		boolean rowUpdated = false;
		
		System.out.println(blog.getBlogTitle());
		System.out.println(blog.getBlogDescription());
		System.out.println(blog.getPostedOn());
		System.out.println(blog.getBlogId());
		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_BLOG); 
			statement.setString(1, blog.getBlogTitle());
			statement.setString(2,blog.getBlogDescription());
			statement.setDate(3, java.sql.Date.valueOf(blog.getPostedOn()));
			statement.setInt(4, blog.getBlogId());
			rowUpdated = statement.executeUpdate() > 0;
			System.out.println(rowUpdated);
		}catch(Exception exception){
			System.out.println(exception);
		}
		return rowUpdated;
	}
}
