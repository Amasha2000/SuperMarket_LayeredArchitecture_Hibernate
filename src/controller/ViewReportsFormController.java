
package controller;

//import db.DatabaseConnection;

import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

public class ViewReportsFormController {
    public void viewMonthlyIncomeReportOnAction(ActionEvent actionEvent) {
        /*try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/MonthlyIncome.jrxml"));
            JasperReport compileReport= JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint= JasperFillManager.fillReport(compileReport,null, DatabaseConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
    }

    public void viewAnnuallyIncomeReportOnAction(ActionEvent actionEvent) {
       /* try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/AnnuallyIncome.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DatabaseConnection.getInstance().getConnection());

      JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
    }

    public void viewDailyIncomeReportOnAction(ActionEvent actionEvent) {
       /* try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/DailyIncome.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DatabaseConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
    }


    public void viewCustomerWiseIncomeReportOnAction(ActionEvent actionEvent) {
        /*try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/CustomerWiseIncome.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DatabaseConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
    }


    public void viewMostMovableItemsReportOnAction(ActionEvent actionEvent) {
        /*try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/MostMovableItemList.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DatabaseConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
    }


    public void viewLeastMovableItemsReportOnAction(ActionEvent actionEvent) {
/*        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/LeastMovableItemList.jrxml"));
            JasperReport compileReport=JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null, DatabaseConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
    }

}

