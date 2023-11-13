/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import com.furniture.ecom._config.PropertiesFileConfig;
import com.furniture.ecom._dto.EnumFlgListDTO;
import com.furniture.ecom._dto.ImageDTO;
import com.furniture.ecom._dto.ProductFilterDTO;
import com.furniture.ecom._dto.RequestDTO;
import com.furniture.ecom._enums.OrderStatus;
import com.furniture.ecom._enums.OrderTracking;
import com.furniture.ecom._enums.RoleType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@Component
public class CommonHelperImpl implements CommonHelper {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String getNameBasedOnLangNo(String nameAr, String nameEn, Integer langNo) {
        if (langNo == 1) {
            return nameAr;
        } else if (langNo == 2) {
            if (nameEn != null && !nameEn.isBlank()) {
                return nameEn;
            }
        }
        return nameAr;
    }

    @Override
    public List<EnumFlgListDTO> getFlgList(String listName, Integer langNo) throws IOException {
        String fileName = GlobalConstants.AR_PROPERTIES;
        if (langNo == 2) {
            fileName = GlobalConstants.EN_PROPERTIES;
        }
        PropertiesFileConfig fileConfig = new PropertiesFileConfig();
        List<EnumFlgListDTO> enumFlgList = new ArrayList<>();
        Properties properties = fileConfig.getPropValues(fileName);
        List<String> enumValues = getEnumValues(listName);
        for (String value : enumValues) {
            EnumFlgListDTO flg = new EnumFlgListDTO();
            flg.setFlgName(value);
            flg.setFlgValue(properties.getProperty(listName + "." + value));
            enumFlgList.add(flg);
        }

        return enumFlgList;
    }

    @Override
    public boolean checkFlgValIsExists(String listName, String flgVal) {
        List<String> flgList = getEnumValues(listName);
        if (flgList == null || flgList.size() < 1) {
            return false;
        }
        return flgList.contains(flgVal);
    }

    @Override
    public String getFlgValBasedOnLang(String listName, String flgVal, Integer langNo) throws IOException {
        String fileName = GlobalConstants.AR_PROPERTIES;
        if (langNo == 2) {
            fileName = GlobalConstants.EN_PROPERTIES;
        }
        PropertiesFileConfig fileConfig = new PropertiesFileConfig();
        Properties properties = fileConfig.getPropValues(fileName);
        return properties.getProperty(listName + "." + flgVal);
    }

    @Override
    public List<String> getEnumValues(String enumName) {
        List<String> enumValues = new ArrayList<>();
        if (enumName != null && !enumName.isBlank()) {
            switch (enumName) {
                case GlobalConstants.ADMIN_ROLE_TYPE:
                    for (RoleType roleType : RoleType.values()) {
                        enumValues.add(roleType.toString());
                    }
                    break;
                case GlobalConstants.OrderStatus:
                    for (OrderStatus orderStatus : OrderStatus.values()) {
                        enumValues.add(orderStatus.toString());
                    }

                    break;
                case GlobalConstants.OrderTracking:
                    for (OrderTracking orderTracking : OrderTracking.values()) {
                        enumValues.add(orderTracking.toString());
                    }
                    break;
                default:
                    enumValues = new ArrayList<>();
                    break;
            }
        }
        return enumValues;
    }

    @Override
    public String buildEmail(String userName, String verifiedCode, String flag) {
        String email = "<!doctype html>\n"
                + "<html>\n"
                + "  <head>\n"
                + "    <meta name=\"viewport\" content=\"width=device-width\">\n"
                + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    <title>Simple Transactional Email</title>\n"
                + "    <style>\n"
                + "    /* -------------------------------------\n"
                + "        INLINED WITH htmlemail.io/inline\n"
                + "    ------------------------------------- */\n"
                + "    /* -------------------------------------\n"
                + "        RESPONSIVE AND MOBILE FRIENDLY STYLES\n"
                + "    ------------------------------------- */\n"
                + "    @media only screen and (max-width: 620px) {\n"
                + "      table[class=body] h1 {\n"
                + "        font-size: 28px !important;\n"
                + "        margin-bottom: 10px !important;\n"
                + "      }\n"
                + "      table[class=body] p,\n"
                + "            table[class=body] ul,\n"
                + "            table[class=body] ol,\n"
                + "            table[class=body] td,\n"
                + "            table[class=body] span,\n"
                + "            table[class=body] a {\n"
                + "        font-size: 16px !important;\n"
                + "      }\n"
                + "      table[class=body] .wrapper,\n"
                + "            table[class=body] .article {\n"
                + "        padding: 10px !important;\n"
                + "      }\n"
                + "      table[class=body] .content {\n"
                + "        padding: 0 !important;\n"
                + "      }\n"
                + "      table[class=body] .container {\n"
                + "        padding: 0 !important;\n"
                + "        width: 100% !important;\n"
                + "      }\n"
                + "      table[class=body] .main {\n"
                + "        border-left-width: 0 !important;\n"
                + "        border-radius: 0 !important;\n"
                + "        border-right-width: 0 !important;\n"
                + "      }\n"
                + "      table[class=body] .btn table {\n"
                + "        width: 100% !important;\n"
                + "      }\n"
                + "      table[class=body] .btn a {\n"
                + "        width: 100% !important;\n"
                + "      }\n"
                + "      table[class=body] .img-responsive {\n"
                + "        height: auto !important;\n"
                + "        max-width: 100% !important;\n"
                + "        width: auto !important;\n"
                + "      }\n"
                + "    }\n"
                + "\n"
                + "    /* -------------------------------------\n"
                + "        PRESERVE THESE STYLES IN THE HEAD\n"
                + "    ------------------------------------- */\n"
                + "    @media all {\n"
                + "      .ExternalClass {\n"
                + "        width: 100%;\n"
                + "      }\n"
                + "      .ExternalClass,\n"
                + "            .ExternalClass p,\n"
                + "            .ExternalClass span,\n"
                + "            .ExternalClass font,\n"
                + "            .ExternalClass td,\n"
                + "            .ExternalClass div {\n"
                + "        line-height: 100%;\n"
                + "      }\n"
                + "      .apple-link a {\n"
                + "        color: inherit !important;\n"
                + "        font-family: inherit !important;\n"
                + "        font-size: inherit !important;\n"
                + "        font-weight: inherit !important;\n"
                + "        line-height: inherit !important;\n"
                + "        text-decoration: none !important;\n"
                + "      }\n"
                + "      #MessageViewBody a {\n"
                + "        color: inherit;\n"
                + "        text-decoration: none;\n"
                + "        font-size: inherit;\n"
                + "        font-family: inherit;\n"
                + "        font-weight: inherit;\n"
                + "        line-height: inherit;\n"
                + "      }\n"
                + "      .btn-primary table td:hover {\n"
                + "        background-color: #34495e !important;\n"
                + "      }\n"
                + "      .btn-primary a:hover {\n"
                + "        background-color: #34495e !important;\n"
                + "        border-color: #34495e !important;\n"
                + "      }\n"
                + "    }\n"
                + "    </style>\n"
                + "  </head>\n"
                + "  <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n"
                + "    <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\">This is preheader text. Some clients will show this text as a preview.</span>\n"
                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n"
                + "      <tr>\n"
                + "        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n"
                + "        <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n"
                + "          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n"
                + "\n"
                + "            <!-- START CENTERED WHITE CONTAINER -->\n"
                + "            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">\n"
                + "\n"
                + "              <!-- START MAIN CONTENT AREA -->\n"
                + "              <tr>\n"
                + "                <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\n"
                + "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n"
                + "                    <tr>\n"
                + "                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n"
                + "                        <p style=\"font-family: sans-serif; font-size: 16px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Hi " + userName + ",</p>\n";
        if (flag == "Password") {
            email += "                        <p style=\"font-family: sans-serif; font-size: 22px; font-weight: bold; margin: 0; Margin-bottom: 15px;\">Reset You Password  .</p>\n"
                    + "                        <p style=\"font-family: sans-serif; font-size: 18px; font-weight: bold; margin: 0; Margin-bottom: 15px;\">Reset Password Code Is : " + verifiedCode + "</p>\n"
                    + "                        <p style=\"font-family: sans-serif; font-size: 22px; font-weight: bold; margin: 0; Margin-bottom: 15px;\">It will be expired after one hour .</p>\n";

        } else {
            email += "                        <p style=\"font-family: sans-serif; font-size: 22px; font-weight: bold; margin: 0; Margin-bottom: 15px;\">Please Verify You Email .</p>\n"
                    + "                        <p style=\"font-family: sans-serif; font-size: 18px; font-weight: bold; margin: 0; Margin-bottom: 15px;\">Verification Code Is : " + verifiedCode + "</p>\n"
                    + "                        <p style=\"font-family: sans-serif; font-size: 22px; font-weight: bold; margin: 0; Margin-bottom: 15px;\">It will be expired after 24 hour .</p>\n";

        }

        email += "                        <p style=\"font-family: sans-serif; font-size: 15px; font-weight: bold; margin: 0; Margin-bottom: 15px;\">Good luck! Thank You....</p>\n"
                + "                      </td>\n"
                + "                    </tr>\n"
                + "                  </table>\n"
                + "                </td>\n"
                + "              </tr>\n"
                + "\n"
                + "            <!-- END MAIN CONTENT AREA -->\n"
                + "            </table>\n"
                + "\n"
                + "            <!-- START FOOTER -->\n"
                + "            <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">\n"
                + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n"
                + "                <tr>\n"
                + "                  <td class=\"content-block\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n"
                + "                    <span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">" + GlobalConstants.COMPANY_ADDRESS + "</span>\n"
                + "                  </td>\n"
                + "                </tr>\n"
                + "              </table>\n"
                + "            </div>\n"
                + "            <!-- END FOOTER -->\n"
                + "\n"
                + "          <!-- END CENTERED WHITE CONTAINER -->\n"
                + "          </div>\n"
                + "        </td>\n"
                + "        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n"
                + "      </tr>\n"
                + "    </table>\n"
                + "  </body>\n"
                + "</html>";
        return email;
    }

    @Override
    public void sendEmail(String usrMail, String emailBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper
                    = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setText(emailBody, true);
            helper.setTo(usrMail);
            helper.setSubject("Verified Code");
            helper.setFrom("ahmedalaahafez95@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    @Override
    public Integer getLngNoFromRequest(RequestDTO req) {
        int langNo = 1;
        if (req != null && req.getData() != null) {
            if (req.getData().get(RequestResponseObj.LNG_NO) != null && (int)req.getData().get(RequestResponseObj.LNG_NO) == 2) {
                langNo = 2;
            }
        }
        return langNo;
    }

    @Override
    public String getProductPagingColumnName(String pagingType) {
        Map<String, String> pagingFilter = new HashMap();
        pagingFilter.put("Category", "catNo");
        pagingFilter.put("Material", "Material");
        pagingFilter.put("Style", "Style");
        pagingFilter.put("Finish", "Finish");
        if (!pagingFilter.containsKey(pagingType)) {
            return null;
        } else {
            return pagingFilter.get(pagingType);
        }
    }

//    @Override
//    public String getProductListQryFromFilter(ProductFilterDTO filter) {
//
//        String hql = "";
//        // availabiltiy
//        if (filter.getAvailablility() != null && (filter.getAvailablility() == 0 || filter.getAvailablility() == 1)) {
//            hql += " AND p.prodAvailability = :" + QueryConstants.AVAILABLITIY;
//        }
//        // category
//        if (filter.getCategoryList() != null && !filter.getCategoryList().isEmpty()) {
//            Set<Integer> categoryList = new HashSet<>();
//            categoryList.addAll(filter.getCategoryList());
//            if (categoryList.size() == 1 && categoryList.iterator().next() == null) {
//                hql += "";
//            } else {
//                hql += " AND p.catNo IN :(" + QueryConstants.CATEGORY + ")";
//            }
//        }
//        //
//        return hql;
//    }

    @Override
    public String uploadFile(ImageDTO imageDto) throws IOException, Exception {
        if (imageDto == null) {
            return null;
        }
        String filePath = "";
        try {
            MultipartFile image = imageDto.getImgData();
            String imageName = image.getOriginalFilename();
            filePath = ImageAttributesConstacts.ROOT_PATH + File.separator + imageDto.getImgType() + File.separator + image.getOriginalFilename();
            image.transferTo(new File(filePath));
            System.out.println("Image Path : " + filePath);
            return imageName;
        } catch (IOException ex) {
            throw ex;
        } catch (IllegalStateException ex) {
            throw ex;
        }
    }
}
