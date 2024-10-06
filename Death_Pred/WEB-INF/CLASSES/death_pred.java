import java.io.IOException;
import java.io.PrintWriter; 
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

public class death_pred extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String gender = request.getParameter("gender");
        int age = Integer.parseInt(request.getParameter("age"));
        int food1 = Integer.parseInt(request.getParameter("food1"));
        int food2 = Integer.parseInt(request.getParameter("food2"));
        int food3 = Integer.parseInt(request.getParameter("food3"));
        int food4 = Integer.parseInt(request.getParameter("food4"));

        int[] toxicityLevels = {7, 7, 6, 3, 2, 8, 9, 3, 9, 8};
        int[] energyLevels = {3, 4, 4, 6, 7, 3, 2, 7, 2, 4};

        int totalToxicity = toxicityLevels[food1] + toxicityLevels[food2] + toxicityLevels[food3] + toxicityLevels[food4];
        int totalEnergy = energyLevels[food1] + energyLevels[food2] + energyLevels[food3] + energyLevels[food4];

        double predictor = (totalToxicity - totalEnergy) / 4.0;
        if (predictor < 0) predictor = 0;
        if (predictor > 10) predictor = 10;

        String lifeExpectancy = calculateLifeExpectancy(gender, age, predictor);
        int yearsLeft = Integer.parseInt(lifeExpectancy.split(" ")[2]); 
        String imageSrc = getImageSource(gender, yearsLeft);

        // Send the response back to the client
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <title>Death Predictor</title>");
        out.println("    <link href=\"https://fonts.googleapis.com/css2?family=Creepster&display=swap\" rel=\"stylesheet\">");
        out.println("    <style>"); 
        out.println("        body {");
        out.println("            display: flex;");
        out.println("            margin: 0;");
        out.println("            font-family: Arial, sans-serif;");
        out.println("            height: 100vh;");
        out.println("            overflow: hidden;");
        out.println("            background-color: black;");
        out.println("            color: white;");
        out.println("        }");
        out.println("        h1 {");
        out.println("            font-family: 'Creepster', cursive;");
        out.println("            color: #ff0000;");
        out.println("            text-align: center;");
        out.println("            font-size: 60px;");
        out.println("            margin-bottom: 40px;");
        out.println("        }");
        out.println("        .left, .right {");
        out.println("            width: 50%;");
        out.println("            padding: 20px;");
        out.println("            box-sizing: border-box;");
        out.println("        }");
        out.println("        .left {");
        out.println("            background-color: #111;");
        out.println("            display: flex;");
        out.println("            flex-direction: column;");
        out.println("            justify-content: center;");
        out.println("            border-right: 2px solid #ff0000;");
        out.println("        }");
        out.println("        .right {");
        out.println("            background-color: #330000;");
        out.println("            display: flex;");
        out.println("            flex-direction: column;");
        out.println("            justify-content: space-between;");
        out.println("            position: relative;");
        out.println("        }");
        out.println("        form {");
        out.println("            display: flex;");
        out.println("            flex-direction: column;");
        out.println("            gap: 15px;");
        out.println("        }");
        out.println("        label, select, input {");
        out.println("            font-size: 16px;");
        out.println("        }");
        out.println("        select, input {");
        out.println("            padding: 10px;");
        out.println("            background-color: #222;");
        out.println("            color: white;");
        out.println("            border: 2px solid #ff0000;");
        out.println("            border-radius: 5px;");
        out.println("        }");
        out.println("        button {");
        out.println("            padding: 10px;");
        out.println("            background-color: #ff0000;");
        out.println("            color: white;");
        out.println("            border: none;");
        out.println("            border-radius: 5px;");
        out.println("            font-size: 16px;");
        out.println("            cursor: pointer;");
        out.println("            transition: background-color 0.3s;");
        out.println("        }");
        out.println("        button:hover {");
        out.println("            background-color: #cc0000;");
        out.println("        }");
        out.println("        .result {");
        out.println("            font-size: 24px;");
        out.println("            font-weight: bold;");
        out.println("            text-align: center;");
        out.println("            margin-top: 20px;");
        out.println("        }");
        out.println("        .images {");
        out.println("            position: relative;");
        out.println("            height: 300px;");
        out.println("        }");
        out.println("        .soil {");
        out.println("            position: absolute;");
        out.println("            bottom: 0;");
        out.println("            width: 100%;");
        out.println("            height: 100px;");
        out.println("            background-image: url('soil.jpg');");
        out.println("            background-size: cover;");
        out.println("        }");
        out.println("        .person {");
        out.println("            position: absolute;");
        out.println("            width: 250px;");
        out.println("            transition: bottom 1s ease;");
        out.println("            left: 50%; /* Center horizontally */");
        out.println("            top: 50%; /* Center vertically */");
        out.println("            transform: translate(-50%, -50%);");
        out.println("            border: #111;");
        out.println("            border-radius: 30%;");
        out.println("        }");
        out.println("        .progress-container {");
        out.println("            position: relative;");
        out.println("            width: 90%;");
        out.println("            height: 20px;");
        out.println("            background-color: #222;");
        out.println("            border-radius: 10px;");
        out.println("            border: 2px solid #ff0000;");
        out.println("            overflow: visible;");
        out.println("            margin: 0 auto;");
        out.println("        }");
        out.println("        .progress-bar {");
        out.println("            height: 100%;");
        out.println("            background-color: #ff0000;");
        out.println("            width: 0%;");
        out.println("            border-radius: 10px;");
        out.println("            transition: width 1s ease;");
        out.println("        }");
        out.println("        .progress-icon {");
        out.println("            position: absolute;");
        out.println("            top: -15px;");
        out.println("            left: -8px;");
        out.println("            width: 50px;");
        out.println("            height: 50px;");
        out.println("            transition: left 1s ease;");
        out.println("        }");
        out.println("        .end-icon {");
        out.println("            position: absolute;");
        out.println("            top: -5px;");
        out.println("            right: -40px;");
        out.println("            width: 30px;");
        out.println("            height: 30px;");
        out.println("            transition: right 1s ease;");
        out.println("        }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"left\">");
        out.println("        <h1>Death Predictor</h1>");
        out.println("        <form id=\"lifePredictorForm\" method=\"POST\" action=\"predictor\">");
        out.println("            <label for=\"gender\">Gender:</label>");
        out.println("            <select id=\"gender\" name=\"gender\" required>");
        out.println("                <option value=\"\" disabled selected>Select gender</option>");
        out.println("                <option value=\"M\">Male</option>");
        out.println("                <option value=\"F\">Female</option>");
        out.println("            </select>");
        out.println("            <label for=\"age\">Age:</label>");
        out.println("            <input type=\"number\" id=\"age\" name=\"age\" required>");
        out.println("            <label>Choose 4 foods:</label>");
        
        // Generate food select options dynamically
        for (int i = 1; i <= 4; i++) {
            out.println("            <select id=\"food" + i + "\" name=\"food" + i + "\" required>");
            out.println("                <option value=\"\" disabled selected>Choose food " + i + "</option>");
            for (int j = 0; j < 10; j++) {
                out.println("                <option value=\"" + j + "\">" + getFoodName(j) + "</option>");
            }
            out.println("            </select>");
        }

        out.println("            <button type=\"submit\">Predict Life Expectancy</button>");
        out.println("        </form>");
        out.println("    </div>");
        out.println("    <div class=\"right\">");
        out.println("        <div id=\"result\" class=\"result\">" + lifeExpectancy + "</div>");
        out.println("        <img id=\"person\" class=\"person\" src=\"" + imageSrc + "\" alt=\"Person Image\">");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    private String calculateLifeExpectancy(String gender, int age, double predictor) {
        int yearsLeft = 0;

        if (age <= 20) {
            if ("M".equals(gender)) {
                if (predictor > 8) return "You will die in 2 weeks.";
                if (predictor > 6) yearsLeft = 20;
                else if (predictor > 4) yearsLeft = 40;
                else if (predictor > 2) yearsLeft = 60;
                else yearsLeft = 70;
            } else {
                if (predictor > 9) return "You will die in 2 weeks.";
                if (predictor > 7) yearsLeft = 20;
                else if (predictor > 5) yearsLeft = 40;
                else if (predictor > 3) yearsLeft = 60;
                else yearsLeft = 70;
            }
        } else if (age <= 40) {
            if ("M".equals(gender)) {
                if (predictor > 6) return "You will die in 2 weeks.";
                if (predictor > 4) yearsLeft = 20;
                else if (predictor > 2) yearsLeft = 40;
                else yearsLeft = 50;
            } else {
                if (predictor > 7) return "You will die in 2 weeks.";
                if (predictor > 5) yearsLeft = 20;
                else if (predictor > 3) yearsLeft = 40;
                else yearsLeft = 50;
            }
        } else if (age <= 60) {
            if ("M".equals(gender)) {
                if (predictor > 4) return "You will die in 2 weeks.";
                if (predictor > 2) yearsLeft = 20;
                else yearsLeft = 30;
            } else {
                if (predictor > 5) return "You will die in 2 weeks.";
                if (predictor > 3) yearsLeft = 20;
                else yearsLeft = 30;
            }
        } else {
            if ("M".equals(gender)) {
                if (predictor > 2) return "You will die in 2 weeks.";
                yearsLeft = 10;
            } else {
                if (predictor > 3) return "You will die in 2 weeks.";
                yearsLeft = 10;
            }
        }

        return "You have " + yearsLeft + " years left.";
    }

    private String getImageSource(String gender, int yearsLeft) {
        String imageSrc = "";

        if ("M".equals(gender)) {
            if (yearsLeft >= 70) imageSrc = "man_70.jpg";
            else if (yearsLeft >= 50) imageSrc = "man_50.png"; 
            else if (yearsLeft >= 40) imageSrc = "man_40.webp";
            else if (yearsLeft >= 30) imageSrc = "man_30.png";
            else if (yearsLeft >= 20) imageSrc = "man_20.jpg";
            else if (yearsLeft >= 10) imageSrc = "man_10.jpg";
            else imageSrc = "man_death.png";
        } else {
            if (yearsLeft >= 70) imageSrc = "woman_70.jpg";
            else if (yearsLeft >= 50) imageSrc = "woman_50.png"; 
            else if (yearsLeft >= 40) imageSrc = "woman_40.webp";
            else if (yearsLeft >= 30) imageSrc = "woman_30.png";
            else if (yearsLeft >= 20) imageSrc = "woman_20.jpg";
            else if (yearsLeft >= 10) imageSrc = "woman_10.jpg";
            else imageSrc = "woman_death.png";
        }

        return imageSrc;
    }

    private String getFoodName(int foodIndex) {
        String[] foodNames = {
           
            "FriedRice", "Noodles", "Biriyani", "LemonRice", "CurdRice", "Pizza",
            "Burger", "Chapatti" ,"Parotta", "GrillChicken",
        };
        return foodNames[foodIndex];
    }
}