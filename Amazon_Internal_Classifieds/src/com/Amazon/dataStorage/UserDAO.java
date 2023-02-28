package com.Amazon.dataStorage;

import java.sql.*;
import com.Amazon.main.UserApp;
import com.Amazon.models.*;


public class UserDAO{
	static User user;
    private DatabaseConnection connectionManager;

	private static UserDAO userDAO = null;
	public static UserDAO getInstance() {
		if (userDAO == null) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}
	
	public UserDAO() {
        try {
        	connectionManager = new DatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	 public User createUser(User user) {
	        try  {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("INSERT INTO Users(username, password, email, phone, isActive) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS );
	            
	            statement.setString(1, user.getUsername());
	            statement.setString(2, user.getPassword());
	            statement.setString(3, user.getEmail());
	            statement.setString(4, user.getPhone());
	            statement.setInt(5, user.isActive());

	            int affectedRows = statement.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Creating user failed, no rows affected.");
	            }

	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    user.setId(generatedKeys.getInt(1));
	                }
	                else {
	                    throw new SQLException("Creating user failed, no ID obtained.");
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }

	        return user;
	    }
	 
	 	public int getUserId(String username, String password) {
	 		try {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("SELECT userId FROM Users WHERE username = ? AND password = ?");
	            statement.setString(1, username);
	            statement.setString(2, password);
	            ResultSet result = statement.executeQuery();
	            if (result.next()) {   
	                user = new User();
	                user.setId(result.getInt("userId"));
	            }
			} catch (Exception e) {
	            System.err.println(e.getMessage());
			}
	 		return user.getId();
	 	}

	    public User findUserById(int id) {
//	        User user = new User();

	        try {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("SELECT * FROM Users WHERE userId = ?");
	            statement.setInt(1, id);

	            ResultSet result = statement.executeQuery();
	            if (result.next()) {
	                user = new User();
	                user.setId(result.getInt("userId"));
	                user.setUsername(result.getString("username"));
	                user.setPassword(result.getString("password"));
	                user.setEmail(result.getString("email"));
	                user.setPhone(result.getString("phone"));
	                user.setBalance(result.getDouble("balance"));
	            }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }

	        return user;
	    }

	    public User findUserByUsername(String username, String password) {
	        User user = new User();

	        try {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");
	            statement.setString(1, username);
	            statement.setString(2, password);

	            ResultSet result = statement.executeQuery();
	            if (result.next()) {
	                
	                user.setId(result.getInt("userId"));
	                user.setUsername(result.getString("username"));
	                user.setPassword(result.getString("password"));
	                user.setEmail(result.getString("email"));
	                user.setPhone(result.getString("phone"));
	                user.setActive(result.getInt("isActive"));
	            }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }

	        return user;
	    }

	    public void updateUser(User user) {
	        try  {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("UPDATE Users SET username = ?, password = ?, email = ?, phone = ?  WHERE id = ?");
	            statement.setString(1, user.getUsername());
	            statement.setString(2, user.getPassword());
	            statement.setString(3, user.getEmail());
	            statement.setString(4, user.getPhone());
//	            statement.setBoolean(5, user.isActive());
	            statement.setInt(5, user.getId());

	            int affectedRows = statement.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Updating user failed, no rows affected.");
	            }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }
	    }

	    public void deleteUser(int id) {
	        try  {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("DELETE FROM Users WHERE id = ?");
	            statement.setInt(1, id);

	            int affectedRows = statement.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Deleting user failed, no rows affected.");
	            }
	        } catch (SQLException e) {
	            System.err.println(e.getMessage());
	        }
	    }
	

		public void updateEmail(String username, String email, String password) {
		    try {
		        PreparedStatement statement = connectionManager.getConnection().prepareStatement(
		            "UPDATE Users SET email = ? WHERE username = ? AND password = ?"
		        );
		        statement.setString(1, email);
		        statement.setString(2, username);
		        statement.setString(3, password);
		        statement.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		
		public void updatePhone(String username, String phone, String password) {
	        try {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement(
	                "UPDATE Users SET phone = ? WHERE username = ? AND password = ?"
	            );
	            statement.setString(1, phone);
	            statement.setString(2, username);
	            statement.setString(3, password);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
			
		
		public void updatePassword(String username, String newPassword, String oldPassword) {
	        try {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("UPDATE Users SET password = ? WHERE username = ? AND password = ?");
	            statement.setString(1, newPassword);
	            statement.setString(2, username);
	            statement.setString(3, oldPassword);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		public void checkBalance(int userId) {
		    try  {
		        String sql = "SELECT balance FROM Users WHERE userId = ?";
		        PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
		        statement.setInt(1, userId);

		        ResultSet result = statement.executeQuery();

		        if (result.next()) {
		            double balance = result.getDouble("balance");
		            System.out.println("\nAvailable balance: " + balance);
		        } else {
		            System.out.println("\nUser not found.");
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		}
		
		public void addBalance(double amount) {
			try{
				String sql = "UPDATE Users SET balance = balance + ? WHERE userId = ?";
				PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
				statement.setDouble(1, amount);
				statement.setInt(2, UserApp.getUserId());
				statement.executeUpdate();				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void updateUserBalance(int userId, double balance) {
			try{
				String sql = "UPDATE Users SET balance = ? WHERE userID = ?";
				PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
				statement.setDouble(1, balance);
				statement.setInt(2, userId);
				statement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	

		
}