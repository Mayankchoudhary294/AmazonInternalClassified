package com.Amazon.dataStorage;

import java.sql.*;
import com.Amazon.models.*;

public class ClassifiedDAO {
	
    private DatabaseConnection connectionManager;	
	private static ClassifiedDAO classifiedDAO = null;
	public static ClassifiedDAO getInstance() {
		if (classifiedDAO == null) {
			classifiedDAO = new ClassifiedDAO();
		}
		return classifiedDAO;
	}
	
	public ClassifiedDAO() {
        try {
        	connectionManager = new DatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }	
	
	public boolean isClassifiedIdValid(int classifiedId) {
	    try {
	        String sql = "SELECT COUNT(*) FROM Classifieds WHERE classifiedId = ?";
	        PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
	        statement.setInt(1, classifiedId);
	        ResultSet result = statement.executeQuery();

	        if (result.next() && result.getInt(1) > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
    public Classified getClassifiedById(int classifiedId) {
        Classified classified = null;
        try  {
            PreparedStatement statement = connectionManager.getConnection().prepareStatement("SELECT * FROM Classifieds WHERE classifiedId=?");
            statement.setInt(1, classifiedId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                classified = new Classified();
                classified.setId(resultSet.getInt("classifiedId"));
                classified.setHeadline(resultSet.getString("headline"));
                classified.setProductName(resultSet.getString("productName"));
                classified.setBrand(resultSet.getString("brand"));
                classified.setCondition(Condition.valueOf(resultSet.getString("condition")));
                classified.setDescription(resultSet.getString("description"));
                classified.setPrice(resultSet.getDouble("price"));
//                classified.setPictures(resultSet.getString("pictures").split(","));
                classified.setCategory(resultSet.getString("category"));
                classified.setUserId(resultSet.getInt("userId"));
                classified.setApprovalStatus(resultSet.getString("approvalStatus"));
                classified.setInventoryStatus(resultSet.getString("inventoryStatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classified;
    }
    
    public void removeClassified(int classifiedId) {
    	try {
    		PreparedStatement statement = connectionManager.getConnection().prepareStatement("DELETE FROM Classifieds WHERE classifiedId=?");
	    	statement.setInt(1, classifiedId);
	    	statement.executeUpdate();
	    	} catch (SQLException e) {
	    	e.printStackTrace();
	    	}
    }
    
    public void updateInvertoryStatus(int classifiedId) {
    	try {
    		PreparedStatement statement = connectionManager.getConnection().prepareStatement("UPDATE Classifieds SET inventoryStatus='Sold' WHERE classifiedId=?");
	    	statement.setInt(1, classifiedId);
	    	statement.executeUpdate();
	    	} catch (SQLException e) {
	    	e.printStackTrace();
	    	}
	}
    
    
}
