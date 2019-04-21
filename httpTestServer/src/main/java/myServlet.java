import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class myServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String user = req.getParameter("user");
        String id = req.getParameter("id");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("	<head>\r\n")
                .append("		<title>Welcome message</title>\r\n")
                .append("	</head>\r\n")
                .append("	<body>\r\n");
        if (user != null && !user.trim().isEmpty()) {
            writer.append("	         User is " + user + ".\r\n");

            writer.append("	         You successfully complete authorization.\r\n");
        } else {
            writer.append("	         You did not entered username!\r\n");
        }
        if(id != null && !id.trim().isEmpty()){
            writer.append("          Id is " + id + "\r\n");
        }else {
            writer.append("          You did not entered a ID.\r\n");
        }
        writer.append("		</body>\r\n")
                .append("</html>\r\n");
    }
}
