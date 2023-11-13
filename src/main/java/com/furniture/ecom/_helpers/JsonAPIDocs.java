/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

/**
 * @author HP
 */
public interface JsonAPIDocs {

    String CUSTOMER_REGISTER = "{\n" + "    \"data\": {\n" + "        \"customer\": {\n" + "            \"custEmail\": \"ahmed1@gmail.com\",\n"
            + "            \"custName\": \"Mostafa Atta\",\n" + "            \"custPassword\": \"custPassword\",\n"
            + "            \"confirmCustPassword\": \"custPassword\"\n" + "        }\n" + "    }\n" + "}";
    String CUSTOMER_LOGIN = "{\n" + "    \"data\": {\n" + "        \"loginData\": {\n" + "            \"email\": \"ahmed@gmail.com\",\n"
            + "            \"password\": \"1995\"\n" + "        }\n" + "    }\n" + "}";
    String CUSTOMER_CHANGE_PASSWORD = "{\n" + "    \"data\": {\n" + "        \"customer\": {\n" + "            \"oldPassword\": \"1995\",\n"
            + "            \"custPassword\": \"ahmedalaa\",\n" + "            \"confirmCustPassword\": \"ahmedalaa\"\n" + "        }\n" + "    }\n"
            + "}";
    String GET_ABOUT_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "    }\n" + "}";
    String GET_ABOUT_ACTIVE_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "    }\n" + "}";
    String GET_ABOUT_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"about\": {\n" + "            \"aboutNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String GET_SHIPPING_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"shipping\": {\n" + "            \"shipNo\": 1\n" + "        }\n" + "    }\n" + "}";
    String DEL_SHIPPING = "{\n" + "    \"data\": {\n" + "        \"shipping\": {\n" + "            \"shipNo\": 1\n" + "        }\n" + "    }\n" + "}";
    String ADD_ABOUT = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"about\": {\n" + "            \"aboutNo\": 2,\n"
            + "            \"enabled\": 1,\n" + "            \"orderNo\": 3,\n"
            + "            \"aboutTxtAr\": \"لنص بالعربيةولنص بالعربيةولنص بالعربيةولنص بالعربيةولنص بالعربية,النص بالعربية\",\n"
            + "            \"aboutTxtEn\": \"English text\"\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_ABOUT = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"about\": {\n" + "            \"aboutNo\": 2,\n"
            + "            \"enabled\": 1,\n" + "            \"orderNo\": 3,\n"
            + "            \"aboutTxtAr\": \"لنص بالعربيةولنص بالعربيةولنص بالعربيةولنص بالعربيةولنص بالعربية,النص بالعربية\",\n"
            + "            \"aboutTxtEn\": \"English text\"\n" + "        }\n" + "    }\n" + "}";
    String DELETE_ABOUT
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"about\": {\n" + "            \"aboutNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String GET_ADMIN_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "    }\n" + "}";
    String GET_ADMIN_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"admin\": {\n" + "            \"admNo\": 5\n" + "        }\n"
            + "    }\n" + "}";
    String ADD_ADMIN
            = "{\n" + "    \"data\": {\n" + "        \"admin\": {\n" + "            \"admNo\": 2,\n" + "            \"admName\": \"Ahmed Alaa\",\n"
            + "            \"admCode\": \"admin\",\n" + "            \"admRoleType\": 1,\n" + "            \"admPassword\": \"19951111\",\n"
            + "            \"confirmAdmPassword\": \"19951111\",\n" + "            \"admPic\": \"ahmed.jpg\",\n"
            + "            \"inActive\": 0\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_ADMIN
            = "{\n" + "    \"data\": {\n" + "        \"admin\": {\n" + "            \"admNo\": 2,\n" + "            \"admName\": \"Ahmed Alaa\",\n"
            + "            \"admCode\": \"admin\",\n" + "            \"admRoleType\": 1,\n" + "            \"admPic\": \"ahmed.jpg\",\n"
            + "            \"inActive\": 0\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_ADMIN_IMG
            = "{\n" + "    \"data\": {\n" + "        \"admin\": {\n" + "            \"admNo\": 2,\n" + "            \"admPic\": \"Ahmed Alaa\"\n"
            + "        }\n" + "    }\n" + "}";
    String UPDATE_CUSTOMER_IMG
            = "{\n" + "    \"data\": {\n" + "        \"customer\": {\n" + "            \"custNo\": 2,\n" + "            \"custPic\": \"Ahmed Alaa\"\n"
            + "        }\n" + "    }\n" + "}";
    String DELETE_ADMIN
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"admin\": {\n" + "            \"admNo\": 5\n" + "        }\n"
            + "    }\n" + "}";
    String CHANGE_PASSWORD
            = "{\n" + "    \"data\": {\n" + "        \"admin\": {\n" + "            \"admNo\": 21,\n" + "            \"oldPassword\": \"ahmedalaa\",\n"
            + "            \"admPassword\": \"ahmedalaa\",\n" + "            \"confirmAdmPassword\": \"ahmedalaa\"\n" + "        }\n"
            + "    }\n" + "}";
    String LOGIN = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"loginData\": {\n" + "            \"admCode\": \"admin\",\n"
            + "            \"password\": \"1995\"\n" + "        }\n" + "    }\n" + "}";
    String GET_CATEGORY_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "        }\n" + "    }\n" + "}";
    String GET_TAXES_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "        }\n" + "    }\n" + "}";
    String GET_DISCOUNTS_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_TAXES
            = "{\n" + "    \"data\": {\n" + "        \"taxes\": {\n" + "            \"taxNo\": 11,\n" + "            \"percentage\": 3.200,\n"
            + "            \"createOn\": \"2020-12-12T00:00:00.000+02:00\",\n" + "            \"enabled\": 1,\n"
            + "            \"createAdm\": 1\n" + "        }\n" + "    }\n" + "}";
    String ADD_CATEGORY
            = "{\n" + "    \"data\": {\n" + "        \"category\": {\n" + "            \"catNo\": 1,\n" + "            \"catNameAr\": \"غرف نوم\",\n"
            + "            \"catNameEn\": \"Bed Rooms\",\n" + "            \"catDsc\": null,\n" + "            \"catDscAr\": null,\n"
            + "            \"catDscEn\": null,\n" + "            \"categoryName\": \"غرف نوم\",\n" + "            \"images\": [\n"
            + "                {\n" + "                    \"imgPath\": \"category1.jpg\"\n" + "                },\n" + "                {\n"
            + "                    \"imgPath\": \"category1.png\"\n" + "                }\n" + "            ]\n" + "        }\n" + "    }\n"
            + "}";

    String GET_CATEGORY_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"category\":{\n" + "        	\"catNo\":2\n" + "        	}\n"
            + "        }\n" + "    }\n" + "}";
    String DELETE_CATEGORY
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"category\":{\n" + "        	\"catNo\":2\n" + "        	}\n"
            + "        }\n" + "    }\n" + "}";
    String UPDATE_CATEGORY = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"category\":{\n" + "        	\"catNo\":3,\n"
            + "        	\"categoryName\":\"Chairs\",\n" + "        	\"catNameAr\":\"3غرف نوم\",\n" + "        	\"catNameEn\":\"Chairs and \",\n"
            + "        	\"catDscAr\":\"تفاصيل عن التصنيف\",\n"
            + "        	\"catDscEn\":\"detail in english about category test update method swagger and post man\"\n" + "        	}\n"
            + "        }\n" + "    }\n" + "}";

    String ADD_PRODUCT
            = "{\n"
            + "    \"data\": {\n"
            + "        \"lngNo\": 1,\n"
            + "        \"product\": {\n"
            + "            \"prodNo\": 3,\n"
            + "            \"prodNameAr\": \"product 3 Ar\",\n"
            + "            \"prodNameEn\": \"product 3 En\",\n"
            + "            \"prodDscAr\": \"text text 3 text text 2 text text 2 text text 2 text text 2 text text 2 text text 2 text text 2\",\n"
            + "            \"styleNo\": 1,\n"
            + "            \"finishNo\": 1,\n"
            + "            \"prodAvailability\": 1,\n"
            + "            \"prodPrice\": 200.0,\n"
            + "            \"prodQuantity\": 4,\n"
            + "            \"dimHight\": 11.0,\n"
            + "            \"dimWidth\": 11.0,\n"
            + "            \"dimDepth\": 11.0,\n"
            + "            \"prodDscEn\": \"1111\",\n"
            + "            \"catNo\": 1,\n"
            + "        \n"
            + "            \"prodColorList\": [\n"
            + "                {\n"
            + "                    \"colorImg\": \"img.jpg\",\n"
            + "                    \"secondColorNo\": null,\n"
            + "                    \"colorNo\": 2\n"
            + "                },\n"
            + "                {\n"
            + "                    \"colorImg\": \"img2.jpg\",\n"
            + "                    \"secondColorNo\": 2,\n"
            + "                    \"colorNo\": 3\n"
            + "                },\n"
            + "                {\n"
            + "                    \"colorImg\": \"img4.jpg\",\n"
            + "                    \"secondColorNo\": 7,\n"
            + "                    \"colorNo\": 4\n"
            + "                }\n"
            + "            ],\n"
            + "            \"prodMaterialList\": [\n"
            + "                {\n"
            + "                    \"materialNo\": 1\n"
            + "                }\n"
            + "            ],\n"
            + "            \"taxNo\": 1,\n"
            + "            \"discountNo\": 1,\n"
            + "            \"warranty\": 5\n"
            + "        }\n"
            + "    }\n"
            + "}";
    String UPDATE_PRODUCT = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"product\": {\n" + "            \"prodNo\": 9,\n"
            + "            \"prodNameAr\": \" سرير\",\n" + "            \"prodNameEn\": \"Cupboard 2\",\n" + "            \"prodName\": null,\n"
            + "            \"prodDscAr\": \"Ahmed Alaa Enter  sdfsadf ahgemd ahmed alaaaa Tbhat Record for test 2\",\n"
            + "            \"prodDsc\": null,\n" + "            \"prodColor\": \"RED\",\n" + "            \"style\": 1,\n"
            + "            \"material\": 1,\n" + "            \"finish\": 1,\n" + "            \"prodAvailability\": 1,\n"
            + "            \"prodPrice\": 22.000,\n" + "            \"prodQuantity\": 2,\n" + "            \"dimHight\": 22.80,\n"
            + "            \"dimWidth\": 11.30,\n" + "            \"dimDepth\": 3.10,\n" + "            \"createAdm\": 1,\n"
            + "            \"createOn\": \"2021-02-22T00:00:00.000+02:00\",\n" + "            \"prodDscEn\": null,\n" + "            \"catNo\": 2\n"
            + "        }\n" + "    }\n" + "}";
    String ADD_PRODUCT_IMG = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"product\": {\n" + "            \"prodNo\": 1,\n"
            + "            \"images\": [\n" + "                {\n" + "                    \"imgPath\": \"category1.jpg\"\n" + "                },\n"
            + "                {\n" + "                    \"imgPath\": \"category1.png\"\n" + "                }\n" + "            ]\n"
            + "        }\n" + "    }\n" + "}";
    String ADD_CATEGORY_IMG = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"category\": {\n" + "            \"catNo\": 1,\n"
            + "            \"images\": [\n" + "                {\n" + "                    \"imgPath\": \"category1.jpg\"\n" + "                },\n"
            + "                {\n" + "                    \"imgPath\": \"category1.png\"\n" + "                }\n" + "            ]\n"
            + "        }\n" + "    }\n" + "}";
    String ADD_ABOUT_IMG = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"about\": {\n"
            //           + "            \"aboutNo\": 1,\n"
            + "            \"images\": [\n" + "                {\n" + "                    \"imgPath\": \"about1.jpg\"\n" + "                },\n"
            + "                {\n" + "                    \"imgPath\": \"about1.png\"\n" + "                }\n" + "            ]\n" + "        }\n"
            + "    }\n" + "}";
    String DELETE_IMAGE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"image\": {\n" + "            \"imgNo\": 6\n" + "        }\n"
            + "    }\n" + "}";
    String UPDATE_IMAGE = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"image\": {\n" + "            \"imgNo\": 5,\n"
            + "            \"imgPath\": \"about_1.jpg\"\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_DISCOUNT
            = "{\n" + "    \"data\": {\n" + "        \"discount\": {\n" + "            \"discountNo\": 1,\n" + "            \"percentage\": 9,\n"
            + "            \"discountMaxValue\" : 100,\n" + "            \"createOn\": \"2020-03-12T00:00:00.000+02:00\",\n"
            + "            \"startDate\": \"2021-11-31\",\n" + "            \"expirDate\": \"2021-12-12\",\n"
            + "            \"enabled\": 0,\n" + "            \"createAdm\": 2\n" + "        }\n" + "    }\n" + "}";
    String GET_SHIPPING_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "    }\n" + "}";
    String ADD_SHIPPING
            = "{\n" + "    \"data\": {\n" + "        \"shipping\": {\n" + "            \"shipNo\": 2,\n" + "            \"shipPrice\": 1,\n"
            + "            \"city\": \"MAKAH\"\n" + "        }\n" + "    }\n" + "}";
    String UPD_SHIPPING
            = "{\n" + "    \"data\": {\n" + "        \"shipping\": {\n" + "            \"shipNo\": 2,\n" + "            \"shipPrice\": 1,\n"
            + "            \"city\": \"MAKAH\"\n" + "        }\n" + "    }\n" + "}";
    // FINISH CONTROLLER
    String GET_PRODUCT_FINISH_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"finish\": {\n" + "            \"finishNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String GET_PRODUCT_FINISH_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2\n" + "    }\n" + "}";
    String GET_COLOR_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2\n" + "    }\n" + "}";
    String ADD_PRODUCT_FINISH
            = "{\n" + "    \"data\": {\n" + "        \"finish\": {\n" + "            \"finishNo\": 1,\n" + "            \"finishNmAr\": \"ورنيش\",\n"
            + "            \"finishNmEn\": \"Lacquer\"\n" + "        }\n" + "    }\n" + "}";
    String ADD_COLOR
            = "{\n" + "    \"data\": {\n" + "        \"color\": {\n" + "            \"colorNo\": 2,\n" + "            \"colorNmAr\": \"أحمر\",\n"
            + "            \"colorNmEn\": \"Red\",\n" + "            \"colorCode\":\"#F00\"\n" + "\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_PRODUCT_FINISH
            = "{\n" + "    \"data\": {\n" + "        \"finish\": {\n" + "            \"finishNo\": 1,\n" + "            \"finishNmAr\": \"ورنيش\",\n"
            + "            \"finishNmEn\": \"Lacquer\"\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_COLOR
            = "{\n" + "    \"data\": {\n" + "        \"color\": {\n" + "            \"colorNo\": 2,\n" + "            \"colorNmAr\": \"أحمر\",\n"
            + "            \"colorNmEn\": \"Red\",\n" + "            \"colorCode\": \"#F00\"\n" + "        }\n" + "    }\n" + "}";
    String DELETE_PRODUCT_FINISH
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"finish\": {\n" + "            \"finishNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String DELETE_COLOR = "{\n" + "    \"data\": {\n" + "        \"color\": {\n" + "            \"colorNo\": 2\n" + "        }\n" + "    }\n" + "}";

    String GET_COLOR_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"color\": {\n" + "            \"colorNo\": 2\n" + "        }\n" + "    }\n" + "}";
    // Material CONTROLLER
    String GET_PRODUCT_MATERIAL_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"material\": {\n" + "            \"materialNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String GET_PRODUCT_MATERIAL_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2\n" + "    }\n" + "}";
    String ADD_PRODUCT_MATERIAL = "{\n" + "    \"data\": {\n" + "        \"material\": {\n" + "            \"materialNo\": 1,\n"
            + "            \"materialNmAr\": \"ورنيش\",\n" + "            \"materialNmEn\": \"Lacquer\"\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_PRODUCT_MATERIAL = "{\n" + "    \"data\": {\n" + "        \"material\": {\n" + "            \"materialNo\": 1,\n"
            + "            \"materialNmAr\": \"ورنيش\",\n" + "            \"materialNmEn\": \"Lacquer\"\n" + "        }\n" + "    }\n" + "}";
    String DELETE_PRODUCT_MATERIAL
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"material\": {\n" + "            \"materialNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    /////
    String GET_PRODUCT_STYLE_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"style\": {\n" + "            \"styleNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String GET_PRODUCT_STYLE_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2\n" + "    }\n" + "}";
    String ADD_PRODUCT_STYLE
            = "{\n" + "    \"data\": {\n" + "        \"style\": {\n" + "            \"styleNo\": 1,\n" + "            \"styleNmAr\": \"ورنيش\",\n"
            + "            \"styleNmEn\": \"Lacquer\"\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_PRODUCT_STYLE
            = "{\n" + "    \"data\": {\n" + "        \"style\": {\n" + "            \"styleNo\": 1,\n" + "            \"styleNmAr\": \"ورنيش\",\n"
            + "            \"styleNmEn\": \"Lacquer\"\n" + "        }\n" + "    }\n" + "}";
    String DELETE_PRODUCT_STYLE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"style\": {\n" + "            \"styleNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String PRE_ADD_UPD = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "    }\n" + "}";
    String GET_CUSTOMER_PROFILE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"customer\":{\n" + "            \"custNo\":1\n" + "        }\n"
            + "        }\n" + "}";
    String GET_CUSTOMER_Orders
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"customer\":{\n" + "            \"custNo\":1\n" + "        }\n"
            + "        }\n" + "}";
    String CONFIRM_EMAIL = "{\n" + "  \"data\": {\n" + "    \"verifiedCode\": \"kuialIcQGP\"\n" + "  }\n" + "}";
    String FORGET_PASSWORD
            = "{\n" + "    \"data\": {\n" + "        \"customer\": {\n" + "            \"custEmail\": \"ahmedalaa0595@gmail.com\"\n" + "        }\n"
            + "    }\n" + "}";
    String RESET_PASSWORD = "{\n" + "    \"data\": {\n" + "        \"customer\": {\n" + "            \"custEmail\": \"ahmedalaa0595@gmail.com\",\n"
            + "            \"confirmCustPassword\": \"ahmedalaa\",\n" + "            \"custPassword\": \"ahmedalaa\",\n"
            + "            \"verifyPasswordCode\": \"hW5ZHHcLHP\"\n" + "        }\n" + "    }\n" + "}";
    String DELETE_PRODUCT
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"product\": {\n" + "            \"prodNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String GET_ALL_CUSTOMERS = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "        }\n" + "}";

    String GET_CUSTOMER_BY_NO
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"customer\":{\n" + "            \"custNo\":1\n" + "        }\n"
            + "        }\n" + "}";
    String DELETE_CUSTOMER
            = "{\n" + "    \"data\": {\n" + "        \"customer\": {\n" + "            \"custNo\": 3\n" + "        }\n" + "    }\n" + "}";
    String GET_PRODUCT_LIST = "{\n"
            + "    \"data\": {\n"
            + "        \"lngNo\": 1,\n"
            + "        \"filter\": {\n"
            + "            \"categoryList\": [\n"
            + "                1,\n"
            + "                2,\n"
            + "                3,\n"
            + "                4,\n"
            + "                5,\n"
            + "                23,\n"
            + "                4,\n"
            + "                1\n"
            + "            ],\n"
            + "            \"materialList\": [\n"
            + "                1,\n"
            + "                2,\n"
            + "                3,\n"
            + "                4,\n"
            + "                5,\n"
            + "                23,\n"
            + "                4,\n"
            + "                1\n"
            + "            ],\n"
            + "            \"styleList\": [\n"
            + "                1,\n"
            + "                2,\n"
            + "                3,\n"
            + "                4,\n"
            + "                5,\n"
            + "                23,\n"
            + "                4,\n"
            + "                1\n"
            + "            ],\n"
            + "            \"colorList\": [\n"
            + "                1,\n"
            + "                2,\n"
            + "                3,\n"
            + "                4,\n"
            + "                5,\n"
            + "                23,\n"
            + "                4,\n"
            + "                1\n"
            + "            ],\n"
            + "            \"finishList\": [\n"
            + "                1,\n"
            + "                2,\n"
            + "                3,\n"
            + "                4,\n"
            + "                5,\n"
            + "                23,\n"
            + "                4,\n"
            + "                1\n"
            + "            ],\n"
            + "            \"availablility\": 1,\n"
            + "            \"maxPrice\": 5444.5,\n"
            + "            \"minPrice\": 4.4,\n"
            + "            \"pageNo\": 2\n"
            + "        }\n"
            + "    }\n"
            + "}";
    String GET_PRODUCT_BY_PAGE
            = "{\n" + "    \"data\": {\n" + "        \"paging\": {\n" + "            \"pageNo\": 2,\n" + "            \"itmPerPage\": 6\n"
            + "        }\n" + "    }\n" + "}";
    String GET_PRODUCT_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"product\": {\n" + "            \"prodNo\": 1\n" + "        }\n"
            + "    }\n" + "}";
    String GET_POLICY_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "        }\n" + "}";
    String GET_POLICY_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"policy\":{\n" + "            \"policyNo\": 2\n" + "        }\n"
            + "        }\n" + "}";
    String DELETE_POLICY
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"policy\":{\n" + "            \"policyNo\": 2\n" + "        }\n"
            + "        }\n" + "}";
    String ADD_POLICY = "{\n" + "    \"data\": {\n" + "        \"lngNo\" : 1,\n" + "        \"policy\": {\n" + "			\"enabled\": 1,\n"
            + "            \"policyTxtAr\": \"kjhdfgdkshjksdfAr testAr testAr testAr testAr testAr \",\n"
            + "            \"policyTxtEn\": \"dsfdsgreghregeg test Test test Test test Test \"\n" + "        }\n" + "    }\n" + "}";
    String UPDATE_POLICY = "{\n" + "    \"data\": {\n" + "        \"lngNo\" : 1,\n" + "        \"policy\": {\n" + "            \"policyNo\" : 7,\n"
            + "			\"enabled\": 1,\n"
            + "            \"policyTxtAr\": \"update Ar update Arupdate Arupdate Arupdate Arupdate Arupdate Ar \",\n"
            + "            \"policyTxtEn\": \"update En update En update En update En update En update En \"\n" + "        }\n" + "    }\n" + "}";
    String ADD_POLICY_IMG = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"policy\": {\n" + "            \"policyNo\": 1,\n"
            + "            \"images\": [\n" + "                {\n" + "                    \"imgPath\": \"policy01.jpg\"\n" + "                },\n"
            + "                {\n" + "                    \"imgPath\": \"policy01.png\"\n" + "                }\n" + "            ]\n"
            + "        }\n" + "    }\n" + "}";
    String GET_CITY_LIST = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1\n" + "        }\n" + "}";
    String GET_CITY_BY_CODE
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"city\":\n" + "            {\n" + "                \"cityNo\" : 1\n"
            + "            }\n" + "    }\n" + "}";
    String DELETE_CITY
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"city\":\n" + "            {\n" + "                \"cityNo\":3\n"
            + "            }\n" + "    }\n" + "}";
    String ADD_CITY = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"city\":\n" + "            {\n"
            + "                \"cityNmAr\":\"Giza\",\n" + "                \"cityNmEn\":\"الجيزة\",\n" + "                \"shipPrice\":\"40\"\n"
            + "            }\n" + "    }\n" + "}";
    String UPDATE_CITY
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"city\":\n" + "            {\n" + "                \"cityNo\" : 3,\n"
            + "                \"cityNmAr\":\"mansoura\",\n" + "                \"cityNmEn\":\"المنصورة\",\n"
            + "                \"shipPrice\":\"85\"\n" + "            }\n" + "    }\n" + "}";
    String GET_FAVORIT_PRODUCTS
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"favorit\": {\n" + "            \"custNo\": 14\n" + "        }\n"
            + "    }\n" + "}";
    String ADD_FAVORIT = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"favorit\": {\n" + "            \"custNo\": 14,\n"
            + "            \"prodNo\":3\n" + "        }\n" + "    }\n" + "}";
    String GET_CART_PRODUCTS
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"cart\": {\n" + "            \"custNo\": 19\n" + "        }\n"
            + "    }\n" + "}";
    String ADD_CART = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"cart\": {\n" + "            \"custNo\": 14,\n"
            + "            \"prodNo\": 1,\n" + "            \"quantity\": 3\n" + "        }\n" + "    }\n" + "}";
    String DELETE_CART = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"cart\": {\n" + "            \"custNo\": 14,\n"
            + "            \"prodNo\": 1\n" + "        }\n" + "    }\n" + "}";
    String DELETE_ALL_CART
            = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 2,\n" + "        \"cart\": {\n" + "            \"custNo\": 14\n" + "        }\n"
            + "    }\n" + "}";
    String GET_DISCOUNT_BY_NO = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"discount\":\n" + "            {\n"
            + "                \"discountNo\" : 1\n" + "            }\n" + "    }\n" + "}";
    String ADD_DISCOUNT = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"discount\":\n" + "            {\n"
            + "                \"percentage\": 10,\n" + "                \"discountMaxValue\": 150\n" + "            }\n" + "    }\n" + "}";
    String DELETE_DISCOUNT = "{\n" + "    \"data\": {\n" + "        \"lngNo\": 1,\n" + "        \"discount\":\n" + "            {\n"
            + "                \"discountNo\":3\n" + "            }\n" + "    }\n" + "}";
    String GET_CUST_ORDERS = "{\n"
            + "    \"data\": {\n"
            + "        \"lngNo\": 1,\n"
            + "        \"order\": {\n"
            + "            \"custNo\": 2\n"
            + "        }\n"
            + "    }\n"
            + "}";
    String GET_ORDER_DETAILS = "{\n"
            + "    \"data\": {\n"
            + "        \"lngNo\": 1,\n"
            + "        \"order\": {\n"
            + "            \"orderNo\": 2\n"
            + "        }\n"
            + "    }\n"
            + "}";
    String UPD_ORDER_STATUS = "{\n"
            + "    \"data\": {\n"
            + "        \"lngNo\": 1,\n"
            + "        \"order\": {\n"
            + "            \"orderNo\": 2,\n"
            + "            \"orderStatus\":\"Delivered\"\n"
            + "        }\n"
            + "    }\n"
            + "}";
    String ADD_ORDER = "";
}
