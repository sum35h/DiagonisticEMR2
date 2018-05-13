package apps.sumesh.android.diagonisticemr;

import java.io.Serializable;

public class EMR_Model implements Serializable
{
        String patient_name;
        String EMR;
        String data;
        String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public EMR_Model()
    {}

    public EMR_Model(String e_mail,String patient_name, String EMR_type, String data) {
        this.patient_name = patient_name;
        this.EMR = EMR_type;
        this.data = data;
        email=e_mail;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getEMR_type() {
        return EMR;
    }

    public void setEMR_type(String EMR_type) {
        this.EMR= EMR_type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
