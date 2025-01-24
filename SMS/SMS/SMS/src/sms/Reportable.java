package sms;

import java.util.List;

// REPORTABLE CLASS
public interface Reportable {
    String generateReport(List<Object> objects);

    void exportToFile();
}
