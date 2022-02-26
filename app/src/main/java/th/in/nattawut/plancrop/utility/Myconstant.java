package th.in.nattawut.plancrop.utility;

public class Myconstant {


    String ip = "https://www.plancropapp.online/";

    //String ip = "192.168.1.12";

    //ล็อคอิน
    private String urlGetUser = "https://www.plancropapp.online/android/php/memberlogin.php";
    private String nameFileSharePreference = "Plan";


    //พืช
    public  String urlCrop1 ="https://www.plancropapp.online/android/php/crop.php";
    //วางแผนเพาะปลูก
    private String urladdPlan = "https://www.plancropapp.online/android/php/insertplan.php";
    //public static String getUrlCrop ="http://192.168.1.122/android/php/selectspinnercrop.php";
    public  String urlCrop ="https://www.plancropapp.online/android/php/selectspinnercrop.php";
    public static String getUrlmid = "https://www.plancropapp.online/android/php/selectmidspinner.php";
    private String urlDeletePlan = "https://www.plancropapp.online/android/php/deleteplan.php";
    private String urlselectPlan = "https://www.plancropapp.online/android/php/selectplanandroid.php";
    private String[] columnPlanString = new String[]{"no","mid","name","cid","crop","yield","pdate","area"};
    private String urlEditPlan = "https://www.plancropapp.online/android/php/editplan.php";

    private String urlselectfarmerandroid = "https://www.plancropapp.online/android/php/selectplanfarmerandroid.php";
    private String[] columnPlanfarmerString = new String[]{"no","pdate","cid","crop","area"};
    public String getUrlselectfarmerandroid() {
        return urlselectfarmerandroid;
    }
    public String[] getColumnPlanfarmerString() {
        return columnPlanfarmerString;
    }

    //เกษตรกร
    private String urlFarmer = "https://www.plancropapp.online/android/php/insertfarmer.php";
    private String urlselectFarmer = "https://www.plancropapp.online/android/php/selectfarmer.php";
    private String[] comlumFarmerString = new String[]{"mid","name","thai","tel","email"};//ต้องแก้ไขยังไม่เสร็จ
    public String[] getComlumFarmerString() {
        return comlumFarmerString;
    }
    private String[] comlumFarmerString1 = new String[]{"mid","userid","pwd","id","name","address","pid","did","sid","vid","tel","email","area"};//ต้องแก้ไขยังไม่เสร็จ
    public String[] getComlumFarmerString1() {
        return comlumFarmerString1;
    }
    private String urlEditFarmer = "https://www.plancropapp.online/android/php/editfarmer.php";
    private String selectfarmerandroid = "https://www.plancropapp.online/android/php/selectfarmerandroid.php";
    public String getSelectfarmerandroid() {
        return selectfarmerandroid;
    }
    private String urlEditFarmerAndroid = "https://www.plancropapp.online/android/php/editfarmerandroid.php";

    //สมาชิก
    private String urlRegister = "https://www.plancropapp.online/android/php/insertmember.php";
    private  String urlselectMember = "https://www.plancropapp.online/android/php/selectmember.php";
    private String[] comlumRegisterString = new String[]{"mid","userid","pwd","id","name","address","pid","did","sid","vid","tel","email"};
    private String urlDeleteFammer = "https://www.plancropapp.online/android/php/deletefammer.php";
    private String urlEditRegister = "https://www.plancropapp.online/android/php/editmember.php";
    private String selectMemberAndroid = "https://www.plancropapp.online/android/php/selectmemberandroid.php";
    private String urlEditMemberAndroid = "https://www.plancropapp.online/android/php/editmemberandroid.php";

    //spinner จังหวัด อำเภอ ตำบล
    public static String getUrlProvince = "https://www.plancropapp.online/android/php/selectprovince.php";
    public static String getUrlAmphur = "https://www.plancropapp.online/android/php/selectdistrict.php";
    public static String getUrlSid = "https://www.plancropapp.online/android/php/selectsubdistrict.php";
    public static String getUrlVid = "https://www.plancropapp.online/android/php/selectsitevillage.php";

    public String UrlProvince = "https://www.plancropapp.online/android/php/selectprovince.php";
    public String UrlAmphur = "https://www.plancropapp.online/android/php/selectdistrict.php";
    public String UrlSid = "https://www.plancropapp.online/android/php/selectsubdistrict.php";
    public String UrlVid = "https://www.plancropapp.online/android/php/selectvillage.php";



    //พืช
    private String urlAddCrop = "https://www.plancropapp.online/android/php/insertcrop.php";
    private String urlselectCrop = "https://www.plancropapp.online/android/php/selectcrop.php";
    private String[] columnCropString = new String[]{"cid","crop","tid","croptype","beginharvest","harvestperiod","yield"};
    //private String urlselectCrop = "http://10.200.1.38/android/php/a.php";
    private String urlDeleteCrop = "https://www.plancropapp.online/android/php/deletecrop.php";
    private String urlEditCrop = "https://www.plancropapp.online/android/php/editcrop.php";

    //ประเภทพืช
    public  String urlCropType = "https://www.plancropapp.online/android/php/selectcroptype.php";
    private String urlAddCropType = "https://www.plancropapp.online/android/php/insertcroptype.php";
    private String urlselectcroptype = "https://www.plancropapp.online/android/php/selectcroptype.php";
    private String urlEditCropType = "https://www.plancropapp.online/android/php/editcroptype.php";
    private String urlDeleteCropType = "https://www.plancropapp.online/android/php/deletecroptype.php";
    private String[] columnCropTypeString = new String[]{"tid","croptype"};

    //การเพาะปลูก
    private String urladdPlant = "https://www.plancropapp.online/android/php/insertplant.php";
    public static String getUrlSite ="https://www.plancropapp.online/android/php/selectspinnersite.php";
    private String urlselectPlant = "https://www.plancropapp.online/android/php/selectplant.php";
    private String[] columnPlantString = new String[]{"no","pdate","cid","yield","crop","area","mid","name","sno","lat","lon"};
    private String urlDeletePlant = "https://www.plancropapp.online/android/php/deleteplant.php";
    private String urlEditPlant = "https://www.plancropapp.online/android/php/editplant.php";


    private String[] columnsitefarmerString = new String[]{"sno","thai"};
    public String[] getColumnsitefarmerString() {
        return columnsitefarmerString;
    }
    public String selectsitefarmer ="https://www.plancropapp.online/android/php/selectsitefarmer.php";//?mid=72
    public String getSelectsitefarmer() {
        return selectsitefarmer;
    }

    //การเพาะปลูก
    public String[] getColumnPlantString() {
        return columnPlantString;
    }
    public String getUrlselectPlant() {
        return urlselectPlant;
    }
    public String getUrladdPlant() {
        return urladdPlant;
    }
    public String getUrlDeletePlant() {
        return urlDeletePlant;
    }
    public String getUrlEditPlant() {
        return urlEditPlant;
    }

    //กิจกรรม
    private String urlAddPlantPicture = "https://www.plancropapp.online/android/php/insertactivity.php";
    private String[] columnPlantPicaString = new String[]{"picno","pdate","description","no"};
    // private String[] columnPlantPicString = new String[]{"SCode","URL"};
    private String[] columnPlantPicString = new String[]{"picno","pdate","description"};
    private String urlselectPlantPic = "https://www.plancropapp.online/android/php/plantactivity.php?no=49";
    private String urlselectImagesPlantPic = "https://www.plancropapp.online/android/php/selectimages.php";
    private String urlPlant = "https://www.plancropapp.online/android/php/plant.php";
    private String urlDeletePlantPic = "https://www.plancropapp.online/android/php/deleteactivity.php";
    private String urlEditPlantPic = "https://www.plancropapp.online/android/php/editactivity.php";
    //private String urlPlant = "http://192.168.1.107/android/php/selectplantfarmer.php";



    //แจ้งความต้องการ
    private String urladdorder = "https://www.plancropapp.online/android/php/insertorder.php";
    private String[] columnOrderString = new String[]{"sdate","edate","crop","qty","name","tel"};
    private String urlselectOrder = "https://www.plancropapp.online/android/php/";
    private String urlDeleteOrder = "https://www.plancropapp.online/android/php/deleteorder.php";
    private String urlEditOrder = "https://www.plancropapp.online/android/php/editorder.php";
    private String urlselectorderreport = "https://www.plancropapp.online/android/php/selectorderreport.php";

    //แปลงเพาะปลูก
    private String urladdSite = "https://www.plancropapp.online/android/php/insertsite.php";
    private String urlselectSite = "https://www.plancropapp.online/android/php/selectsite.php";
    private String urlDeleteSite = "https://www.plancropapp.online/android/php/deletesite.php";
    private String urlEditSite = "https://www.plancropapp.online/android/php/editsite.php";
    private String urlSiteFarmer = "https://www.plancropapp.online/android/php/farmer.php";
    private String urlSelectSiteVillageFarmer = "https://www.plancropapp.online/android/php/selectsitevillagefarmer.php";



    //ล็อคอิน
    public String getUrlGetUser() {
        return urlGetUser;
    }
    public String getNameFileSharePreference() {
        return nameFileSharePreference;
    }

    //เกษตรกร
    public String getUrlFarmer() {
        return urlFarmer;
    }
    public String getUrlselectFarmer() {
        return urlselectFarmer;
    }
    public String getUrlEditFarmer() {
        return urlEditFarmer;
    }
    public String getUrlEditFarmerAndroid() {
        return urlEditFarmerAndroid;
    }

    //สามชิก
    public String getUrlRegister() {
        return urlRegister;
    }
    public String[] getComlumRegisterString() {
        return comlumRegisterString;
    }
    public String getUrlselectMember() {
        return urlselectMember;
    }
    public String getUrlDeleteFammer() {
        return urlDeleteFammer;
    }
    public String getUrlEditRegister() {
        return urlEditRegister;
    }
    public String getSelectMemberAndroid() {
        return selectMemberAndroid;
    }
    public String getUrlEditMemberAndroid() {
        return urlEditMemberAndroid;
    }

    //วางแผนเพาะปลูก
    public String[] getColumnPlanString() {
        return columnPlanString;
    }
    /*public String getUrlGetPlan() {
        return urlGetPlan;
    }*/
    public String getUrlDeletePlan() {
        return urlDeletePlan;
    }
    public String getUrladdPlan() {
        return urladdPlan;
    }
    public String getUrlselectPlan() {
        return urlselectPlan;
    }
    public String getUrlEditPlan() {
        return urlEditPlan;
    }
    //เกษตรกร

    //พืช

    public String getUrlCrop() {
        return urlCrop;
    }
    public String getUrlAddCrop() {
        return urlAddCrop;
    }
    public String getUrlselectCrop() {
        return urlselectCrop;
    }
    public String[] getColumnCropString() {
        return columnCropString;
    }
    public String getUrlDeleteCrop() {
        return urlDeleteCrop;
    }
    public String getUrlEditCrop() {
        return urlEditCrop;
    }
    public String getUrlCrop1() {
        return urlCrop1;
    }

    //ประเภทพืช
    public String getUrlCropType() {
        return urlCropType;
    }
    public String getUrlAddCropType() {
        return urlAddCropType;
    }
    public String[] getColumnCropTypeString() {
        return columnCropTypeString;
    }
    public String getUrlselectcroptype() {
        return urlselectcroptype;
    }
    public String getUrlEditCropType() {
        return urlEditCropType;
    }
    public String getUrlDeleteCropType() {
        return urlDeleteCropType;
    }

    //กิจกกรม
    public String getUrlAddPlantPicture() {
        return urlAddPlantPicture;
    }
    public String[] getColumnPlantPicString() {
        return columnPlantPicString;
    }
    public String getUrlselectImagesPlantPic() {
        return urlselectImagesPlantPic;
    }
    public String getUrlselectPlantPic() {
        return urlselectPlantPic;
    }
    public String[] getColumnPlantPicaString() {
        return columnPlantPicaString;
    }
    public String getUrlPlant() {
        return urlPlant;
    }
    public String getUrlDeletePlantPic() {
        return urlDeletePlantPic;
    }

    public String getUrlEditPlantPic() {
        return urlEditPlantPic;
    }

    //แจ้งความต้องการ
    public String getUrladdorder() {
        return urladdorder;
    }
    public String getUrlselectOrder() {
        return urlselectOrder;
    }
    public String[] getColumnOrderString() {
        return columnOrderString;
    }
    public String getUrlDeleteOrder() {
        return urlDeleteOrder;
    }
    public String getUrlEditOrder() {
        return urlEditOrder;
    }
    public String getUrlselectorderreport() {
        return urlselectorderreport;
    }


    //แปลงเพาะปลูก
    public String getUrladdSite() {
        return urladdSite;
    }
    public String getUrlselectSite() {
        return urlselectSite;
    }
    public String getUrlDeleteSite() {
        return urlDeleteSite;
    }
    public String getUrlEditSite() {
        return urlEditSite;
    }
    public String getUrlSiteFarmer() {
        return urlSiteFarmer;
    }
    public String getUrlSelectSiteVillageFarmer() {
        return urlSelectSiteVillageFarmer;
    }

    public String getUrlProvince() {
        return UrlProvince;
    }
    public String getUrlAmphur() {
        return UrlAmphur;
    }
    public String getUrlSid() {
        return UrlSid;
    }
    public String getUrlVid() {
        return UrlVid;
    }
}
