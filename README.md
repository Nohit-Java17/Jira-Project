# DỰ ÁN WEB THƯƠNG MẠI ĐIỆN TỬ
Trang web thương mại điện tử được thiết lập để phục vụ một phần hoặc toàn bộ quy trình của hoạt động mua bán hàng hóa hay cung ứng dịch vụ, từ trưng bày giới thiệu hàng hóa, dịch vụ đến giao kết hợp đồng, cung ứng dịch vụ, thanh toán và dịch vụ sau bán hàng.

## LINK DEMO
<div align="center">

[Click vào đây để xem chi tiết](https://jira-project.herokuapp.com)

</div>

## HÌNH ẢNH DEMO
<p align="center">
<img src="https://raw.githubusercontent.com/Tynab/Jira-Project/main/temp/pic/0.png"></img>
</p>

## VIDEO DEMO
<div align="center">
[![IMAGE ALT TEXT HERE] (https://img.youtube.com/vi/0mZWlULDHVA/0.jpg)] (https://www.youtube.com/watch?v=0mZWlULDHVA)

</div>

## CẤU HÌNH API REFRESH TOKEN
```java
// Refresh token
    @GetMapping(TOKEN_VIEW + REFRESH_VIEW)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws StreamWriteException, DatabindException, IOException {
        var header = request.getHeader(AUTHORIZATION);
        // check token format in authorization header
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            // get token from authorization header
            try {
                var refreshToken = header.substring(TOKEN_PREFIX.length());
                var algorithm = HMAC256(SECRET_KEY.getBytes());
                var user = userService.getUser(require(algorithm).build().verify(refreshToken).getSubject());
                var tokens = new HashMap<>();
                tokens.put(ACCESS_TOKEN_KEY,
                        create().withSubject(user.getEmail())
                                .withExpiresAt(new Date(currentTimeMillis() + EXPIRATION_TIME))
                                .withIssuer(request.getRequestURL().toString())
                                .withClaim(ROLE_CLAIM_KEY,
                                        singleton(new Role(ROLE_PREFIX + user.getRole().getName().toUpperCase()))
                                                .stream().map(Role::getName).collect(toList()))
                                .sign(algorithm));
                tokens.put(REFRESH_TOKEN_KEY, refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                var errorMsg = e.getMessage();
                response.setHeader(ERROR_HEADER_KEY, errorMsg);
                response.setStatus(FORBIDDEN.value());
                var error = new HashMap<>();
                error.put(ERROR_MESSAGE_KEY, errorMsg);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
```

## DEMO APPLICATION CONTROLLER 
```java
@Controller
@RequestMapping("")
public class ApplicationController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private StringUtil stringUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;

    // Load register
    @GetMapping(REGISTER_VIEW)
    public ModelAndView register() {
        // check current account still valid
        if (authenticationUtil.getAccount() == null) {
            var mav = new ModelAndView(REGISTER_TEMP);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
        } else {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        }
    }

    // Register
    @PostMapping(REGISTER_VIEW)
    public String register(KhachHang khachHang) {
        var trueEmail = stringUtil.removeSpCharsBeginAndEnd(khachHang.getEmail()).toLowerCase();
        mIsMsgShow = true;
        // check email is already exist
        if (khachHangService.getKhachHang(trueEmail) != null) {
            mMsg = "Email này đã được đăng ký!";
            return REDIRECT_PREFIX + REGISTER_VIEW;
        } else {
            khachHang.setEmail(trueEmail);
            khachHang.setIdTinhThanh(DEFAULT_PROVINCE);
            khachHang.setVaiTro(DEFAULT_ROLE);
            khachHang = khachHangService.saveKhachHang(khachHang);
            gioHangService.createGioHang(khachHang);
            mMsg = "Tài khoản đã được đăng ký thành công!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }

    // Load login
    @GetMapping(LOGIN_VIEW)
    public ModelAndView login(boolean error) {
        // check current account still valid
        if (authenticationUtil.getAccount() == null) {
            var mav = new ModelAndView(LOGIN_TEMP);
            // login failed
            if (error) {
                mIsMsgShow = true;
                mMsg = "Tài khoản đăng nhập chưa đúng!";
            }
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
        } else {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        }
    }

    // Load password-reset
    @GetMapping(PASSWORD_RESET_VIEW)
    public ModelAndView resetPassword() {
        var mav = new ModelAndView(PASSWORD_RESET_TEMP);
        mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
        return mav;
    }

    // quên mật khẩu
    @PostMapping(PASSWORD_RESET_VIEW)
    public String resetPassword(String email) throws UnsupportedEncodingException, MessagingException {
        var trueEmail = stringUtil.removeSpCharsBeginAndEnd(email).toLowerCase();
        mIsMsgShow = true;
        // check email is already exist
        if (khachHangService.getKhachHang(trueEmail) == null) {
            mMsg = "Email này chưa được đăng ký. Vui lòng thử lại!";
            return REDIRECT_PREFIX + PASSWORD_RESET_VIEW;
        } else {
            khachHangService.resetPassword(email);
            mMsg = "Mật khẩu mới đã gửi về email. Vui lòng kiểm tra lại!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }
}
```

## AUTHENTICATION UTIL 
```java
@Component
public class AuthenticationUtil {
    @Autowired
    private KhachHangService khachHangService;

    // Get current user account from SecurityContextHolder
    public KhachHang getAccount() {
        var authentication = getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? null
                // Get user by email address
                : khachHangService.getKhachHang(authentication.getName());
    }
}
```

## TEMPLATE CONSTANTS
```java
public class TemplateConstant {
    public static final String NOT_FOUND_TEMP = "404";
    public static final String BLANK_TEMP = "blank";
    public static final String ABOUT_TEMP = "about";
    public static final String CART_TEMP = "cart";
    public static final String CATEGORY_TEMP = "category";
    public static final String CHECKOUT_TEMP = "checkout";
    public static final String CONTACT_TEMP = "contact";
    public static final String DETAIL_TEMP = "detail";
    public static final String HISTORY_TEMP = "history";
    public static final String INDEX_TEMP = "index";
    public static final String LOGIN_TEMP = "login";
    public static final String ORDER_TEMP = "order";
    public static final String PASSWORD_RESET_TEMP = "password-reset";
    public static final String PRODUCT_TEMP = "product";
    public static final String PROFILE_TEMP = "profile";
    public static final String REGISTER_TEMP = "register";
}
```

### THÀNH VIÊN
Nhóm NOHIT gồm các thành viên:

<img src="https://raw.githubusercontent.com/Tynab/Jira-Project/main/temp/pic/2.png" align="right" width="21%" height="21%"></img>
<div style="display:flex;">

- Nguyễn Đặng Trường An
- Trần Gia Bảo (đã rời nhóm)
- Cao Đức Mạnh
- Đặng Bá Quí (đã rời nhóm)
- Nguyễn Tiến Đạt

</div>

### TÍCH HỢP
<img src="https://raw.githubusercontent.com/Tynab/CRM-Project/master/temp/pic/1.png" align="left" width="3%" height="3%"></img>
<div style="display:flex;">

- Java JWT » 4.0.0

</div>
