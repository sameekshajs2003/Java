package model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
 private Connection connection;
 public UserModel() {
 try {
 Class.forName("com.mysql.cj.jdbc.Driver");
 connection = DriverManager.getConnection("jdbc:mysql://localhost/exam", "root", "Samijs@7");
 } catch (SQLException | ClassNotFoundException e) {
 e.printStackTrace();
 }
 }
 public String studentSrn;
 public String teacherIrn;
 
 public int registerUser(String username, String password, String idNumber,String deptk,String ext, String userType) {
 try {
 String tableName = "";
 String idColumnName = "";
 String deptName="";
 String extraName="";
 if (userType.equalsIgnoreCase("student")) {
 tableName = "Students";
 idColumnName = "srn";
 deptName="dept";
 extraName="sem";
 } else if (userType.equalsIgnoreCase("teacher")) {
 tableName = "Teachers";
 idColumnName = "irn";
 deptName="depti";
 extraName="sub";
 } else {
 return -1; // Return -1 for failure
 }
 PreparedStatement statement = connection.prepareStatement(
 "INSERT INTO " + tableName + " (" + idColumnName + ", username, password," + deptName + "," + extraName + ") VALUES (?, ?, ?,?,?)",
 PreparedStatement.RETURN_GENERATED_KEYS
 );
 statement.setString(1, idNumber);
 statement.setString(2, username);
 statement.setString(3, password);
 statement.setString(4, deptk);
 statement.setString(5, ext);
 int rowsAffected = statement.executeUpdate();
 if (rowsAffected > 0) {
 ResultSet generatedKeys = statement.getGeneratedKeys();
 if (generatedKeys.next()) {
	 if (userType.equalsIgnoreCase("student")) {
         // Create a table for the student with their SRN as table name
         String createTableQuery = "CREATE TABLE IF NOT EXISTS " + idNumber + " (" +
                 "exam_date VARCHAR(255) ," +
                 "exam_subject VARCHAR(255)," +
                 "exam_type VARCHAR(255)," +
                 "marks INT," +
                 "review VARCHAR(255)" +
                 ")";
         PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
         createTableStatement.executeUpdate();
     }
      // Return the generated ID
 }
 return generatedKeys.getInt(1); // Return the generated ID
 }

 return -1; // Return -1 for failure
 } catch (SQLException e) {
 e.printStackTrace();
 return -1; // Return -1 for failure
 }
 }
 
 public String authenticateUser(String username, String password) {
	    try {
	        PreparedStatement studentStatement = connection.prepareStatement(
	                "SELECT * FROM Students WHERE username=? AND password=?"
	        );
	        studentStatement.setString(1, username);
	        studentStatement.setString(2, password);
	        ResultSet studentResult = studentStatement.executeQuery();

	        if (studentResult.next()) {
	            studentSrn = studentResult.getString("srn");
	            return "student";
	        }

	        PreparedStatement teacherStatement = connection.prepareStatement(
	                "SELECT * FROM Teachers WHERE username=? AND password=?"
	        );
	        teacherStatement.setString(1, username);
	        teacherStatement.setString(2, password);
	        ResultSet teacherResult = teacherStatement.executeQuery();

	        if (teacherResult.next()) {
	            teacherIrn = teacherResult.getString("irn");
	            return "teacher";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
 
 public boolean changePassword(String newPassword, String userType) {
	    try {
	        String idColumnName = "";
	        if (userType.equalsIgnoreCase("student")) {
	            idColumnName = "srn";
	        } else if (userType.equalsIgnoreCase("teacher")) {
	            idColumnName = "irn";
	        } else {
	            return false; // Invalid user type
	        }

	        PreparedStatement statement = connection.prepareStatement(
	                "UPDATE " + (userType.equalsIgnoreCase("student") ? "Students" : "Teachers") +
	                " SET password = ? WHERE " + idColumnName + " = ?"
	        );
	        statement.setString(1, newPassword);
	        statement.setString(2, userType.equalsIgnoreCase("student") ? studentSrn : teacherIrn);
	        int rowsAffected = statement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


 // Method to fetch list of students
 public String[] getStudentsList() {
	    List<String> students = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement("SELECT srn FROM Students");
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            students.add(resultSet.getString("srn"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return students.toArray(new String[0]);
	}

	public String[] getInstructorsList() {
	    List<String> instructors = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement("SELECT irn FROM Teachers");
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            instructors.add(resultSet.getString("irn"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return instructors.toArray(new String[0]);
	}

	// Method to create a group with multiple students
	public boolean createGroup(String groupName, String[] selectedStudents, String instructorId) {
	    try {
	        // First, insert the group name into the Classgroup table
	        PreparedStatement groupStatement = connection.prepareStatement(
	                "INSERT INTO Classgroup (group_name, instructor_id) VALUES (?, ?)",
	                PreparedStatement.RETURN_GENERATED_KEYS
	        );
	        groupStatement.setString(1, groupName);
	        groupStatement.setString(2, instructorId);
	        groupStatement.executeUpdate();

	        ResultSet generatedKeys = groupStatement.getGeneratedKeys();
	        int groupId = -1;
	        if (generatedKeys.next()) {
	            groupId = generatedKeys.getInt(1);
	        } else {
	            return false; // Failed to get the generated group ID
	        }

	        // Then, insert each selected student into the GroupStudents table
	        PreparedStatement membershipStatement = connection.prepareStatement(
	                "INSERT INTO GroupStudents (group_id, student_srn) VALUES (?, ?)"
	        );
	        for (String student : selectedStudents) {
	            membershipStatement.setInt(1, groupId);
	            membershipStatement.setString(2, student);
	            membershipStatement.addBatch();
	        }

	        // Execute batch insert
	        int[] rowsAffected = membershipStatement.executeBatch();

	        // Check if any rows were affected
	        for (int row : rowsAffected) {
	            if (row <= 0) {
	                return false; // If any row has not been affected, return false
	            }
	        }

	        return true; // If all rows have been affected, return true
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean deleteGroup(String groupName) {
	    try {
	        PreparedStatement statement = connection.prepareStatement("DELETE FROM Classgroup WHERE group_name = ?");
	        statement.setString(1, groupName);
	        int rowsAffected = statement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public String[][] getGroupsAndStudents() {
	    List<String[]> groupsAndStudentsList = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	                "SELECT group_name, GROUP_CONCAT(student_srn) AS students FROM Classgroup cg JOIN GroupStudents gs ON cg.id = gs.group_id GROUP BY cg.id"
	        );
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            String groupName = resultSet.getString("group_name");
	            String students = resultSet.getString("students");
	            groupsAndStudentsList.add(new String[]{groupName, students});
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return groupsAndStudentsList.toArray(new String[0][]);
	}

	public String[] getStudentsInGroup(String groupName) {
	    List<String> studentsList = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	            "SELECT student_srn FROM GroupStudents gs JOIN Classgroup cg ON gs.group_id = cg.id WHERE cg.group_name = ?"
	        );
	        statement.setString(1, groupName);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            studentsList.add(resultSet.getString("student_srn"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return studentsList.toArray(new String[0]);
	}

	// Add this method to submit exam results
	public boolean submitExamResults(String groupName,String studentSrn, String examSubject, String examType, String examDate, int marks, String review) {
	    try {
	        // Check if the instructor is in the same group as the student
	        PreparedStatement checkStatement = connection.prepareStatement(
	            "SELECT * FROM Classgroup cg JOIN GroupStudents gs ON cg.id = gs.group_id WHERE gs.student_srn = ? AND cg.instructor_id = ?"
	        );
	        checkStatement.setString(1, studentSrn);
	        checkStatement.setString(2, teacherIrn);
	        ResultSet resultSet = checkStatement.executeQuery();
	        if (!resultSet.next()) {
	            // If the instructor is not in the same group as the student, return false
	            return false;
	        }
	        // If the instructor is in the same group as the student, proceed to insert exam results
	        PreparedStatement statement = connection.prepareStatement(
	            "INSERT INTO ExamResults (group_name,student_srn, exam_subject, exam_type, exam_date, marks, review) VALUES (?,?, ?, ?, ?, ?, ?)"
	        );
	        statement.setString(1, groupName);
	        statement.setString(2, studentSrn);
	        statement.setString(3, examSubject);
	        statement.setString(4, examType);
	        statement.setString(5, examDate);
	        statement.setInt(6, marks);
	        statement.setString(7, review);
	        int rowsAffected = statement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	// Inside UserModel class
	public String[][] getExamResults() {
        List<String[]> examResultsList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT exam_date, exam_subject, exam_type, marks, review FROM " + studentSrn
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String examDate = resultSet.getString("exam_date");
                String examSubject = resultSet.getString("exam_subject");
                String examType = resultSet.getString("exam_type");
                int marks = resultSet.getInt("marks");
                String review = resultSet.getString("review");
                examResultsList.add(new String[]{examDate, examSubject, examType, String.valueOf(marks), review});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examResultsList.toArray(new String[0][]);
    }
	// Add this method to fetch exam results based on criteria
	public void getAndInsertExamResults(String groupName, String examDate, String examSubject, String examType) {
	    List<String[]> examResultsList = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	            "SELECT exam_subject, marks, review ,student_srn FROM ExamResults WHERE group_name = ? AND exam_date = ? AND exam_subject = ? AND exam_type = ?"
	        );
	        statement.setString(1, groupName);
	        statement.setString(2, examDate);
	        statement.setString(3, examSubject);
	        statement.setString(4, examType);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            String subject = resultSet.getString("exam_subject");
	            int marks = resultSet.getInt("marks");
	            String review = resultSet.getString("review");
	            String srn = resultSet.getString("student_srn");
	            examResultsList.add(new String[]{examDate, subject, examType, String.valueOf(marks), review, srn});
	            
	            // Insert the exam result into the corresponding table
	            String query = "INSERT INTO " + srn + " (exam_date, exam_subject, exam_type, marks, review) VALUES (?, ?, ?, ?, ?)";
	            try {
	                PreparedStatement insertStatement = connection.prepareStatement(query);
	                insertStatement.setString(1, examDate); // examDate
	                insertStatement.setString(2, subject); // examSubject
	                insertStatement.setString(3, examType); // examType
	                insertStatement.setInt(4, marks); // marks
	                insertStatement.setString(5, review); // review
	                insertStatement.executeUpdate();
	            } catch (SQLException e) {
	                e.printStackTrace();
	                // Handle SQLException
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



	public String[] getGroupNames() {
	    List<String> groupNamesList = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement("SELECT group_name FROM Classgroup");
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            String groupName = resultSet.getString("group_name");
	            groupNamesList.add(groupName);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return groupNamesList.toArray(new String[0]);
	}

	public String[][] getScheduledExams() {
	    List<String[]> scheduledExamsList = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	                "SELECT subject, type, date_scheduled, duration, total_marks, groupname FROM Exams"
	        );
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            String subject = resultSet.getString("subject");
	            String type = resultSet.getString("type");
	            String dateScheduled = resultSet.getString("date_scheduled");
	            int duration = resultSet.getInt("duration");
	            int totalMarks = resultSet.getInt("total_marks");
	            String groupname = resultSet.getString("groupname");
	            scheduledExamsList.add(new String[]{subject, type, dateScheduled, String.valueOf(duration), String.valueOf(totalMarks), groupname});
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return scheduledExamsList.toArray(new String[0][]);
	}
	

	public boolean registerExam(String examName, String examDate) {
	    try {
	        PreparedStatement checkStatement = connection.prepareStatement(
	                "SELECT * FROM RegisteredExams WHERE exam_name = ? AND exam_date = ? AND student_srn = ?"
	        );
	        checkStatement.setString(1, examName);
	        checkStatement.setString(2, examDate);
	        checkStatement.setString(3, studentSrn);
	        ResultSet resultSet = checkStatement.executeQuery();
	        if (resultSet.next()) {
	            return false;
	        }
	        PreparedStatement statement = connection.prepareStatement(
	                "INSERT INTO RegisteredExams (exam_name, exam_date, student_srn) VALUES (?, ?, ?)"
	        );
	        statement.setString(1, examName);
	        statement.setString(2, examDate);
	        statement.setString(3, studentSrn);
	        int rowsAffected = statement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public String[][] getUpcomingExams(String groupName) {
	    List<String[]> upcomingExamsList = new ArrayList<>();
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	                "SELECT subject, type, date_scheduled, duration, total_marks, groupname FROM Exams WHERE groupname = ? AND date_scheduled >= CURDATE()"
	        );
	        statement.setString(1, groupName);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            String subject = resultSet.getString("subject");
	            String type = resultSet.getString("type");
	            String dateScheduled = resultSet.getString("date_scheduled");
	            int duration = resultSet.getInt("duration");
	            int totalMarks = resultSet.getInt("total_marks");
	            String groupname = resultSet.getString("groupname");
	            upcomingExamsList.add(new String[]{subject, type, dateScheduled, String.valueOf(duration), String.valueOf(totalMarks), groupname});
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return upcomingExamsList.toArray(new String[0][]);
	}
	
	public String[] getstudentDetails() {
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	            "SELECT srn, username, dept, sem FROM Students WHERE srn = ?"
	        );
	        statement.setString(1, studentSrn);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            String srn = resultSet.getString("srn");
	            String username = resultSet.getString("username");
	            String dept = resultSet.getString("dept");
	            String sem = resultSet.getString("sem");
	            return new String[]{srn, username, dept, sem};
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // Return null if no student details found
	}
	
	public String[] getteacherDetails() {
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	            "SELECT irn, username, depti, sub FROM Teachers WHERE irn = ?"
	        );
	        statement.setString(1, teacherIrn);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            String irn = resultSet.getString("irn");
	            String username = resultSet.getString("username");
	            String dept = resultSet.getString("depti");
	            String sub = resultSet.getString("sub");
	            return new String[]{irn, username, dept, sub};
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // Return null if no student details found
	}
	// Inside UserModel class




	public String[] getUserGroups() {
	    List<String> userGroupsList = new ArrayList<>();
	    try {
	        String query = "SELECT group_name FROM Classgroup cg JOIN GroupStudents gs ON cg.id = gs.group_id WHERE gs.student_srn = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, studentSrn);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            String groupName = resultSet.getString("group_name");
	            userGroupsList.add(groupName);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return userGroupsList.toArray(new String[0]);
	}

	// Add this method to schedule an exam
	public boolean scheduleExam(String subject, String type, String dateScheduled, int duration, int totalMarks, String groupname) {
	    try {
	        PreparedStatement statement = connection.prepareStatement(
	                "INSERT INTO Exams (subject, type, date_scheduled, duration, total_marks, groupname) VALUES (?, ?, ?, ?, ?, ?)"
	        );
	        statement.setString(1, subject);
	        statement.setString(2, type);
	        statement.setString(3, dateScheduled);
	        statement.setInt(4, duration);
	        statement.setInt(5, totalMarks);
	        statement.setString(6, groupname);
	        int rowsAffected = statement.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}