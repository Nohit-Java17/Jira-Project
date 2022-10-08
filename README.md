# DỰ ÁN WEB THƯƠNG MẠI ĐIỆN TỬ
Trang web thương mại điện tử được thiết lập để phục vụ một phần hoặc toàn bộ quy trình của hoạt động mua bán hàng hóa hay cung ứng dịch vụ, từ trưng bày giới thiệu hàng hóa, dịch vụ đến giao kết hợp đồng, cung ứng dịch vụ, thanh toán và dịch vụ sau bán hàng.

## LINK DEMO
<div align="center">

[Click vào đây để xem chi tiết](https://jira-project.herokuapp.com)

</div>

## HÌNH ẢNH DEMO
<p align="center">
<img src="https://raw.githubusercontent.com/Nohit-Java17/Jira-Project/main/pic/0.jpg"></img>
</p>

## VIDEO DEMO
<div align="center">

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/YXG24rEs8Q4/0.jpg)](https://youtu.be/YXG24rEs8Q4)

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

## EER Diagram
<p align="center">
<img src="https://raw.githubusercontent.com/Nohit-Java17/Jira-Project/main/design/EER%20Diagram.jpg"></img>
</p>

### THÀNH VIÊN
Nhóm NOHIT gồm các thành viên:

<img src="https://raw.githubusercontent.com/Nohit-Java17/Jira-Project/main/pic/2.jpg" align="right" width="21%" height="21%"></img>
<div style="display:flex;">

- Nguyễn Đặng Trường An (team lead)
- Trần Gia Bảo (đã rời nhóm)
- Cao Đức Mạnh
- Đặng Bá Quí (đã rời nhóm)
- Nguyễn Tiến Đạt

</div>

### TÍCH HỢP
<img src="https://raw.githubusercontent.com/Nohit-Java17/Jira-Project/main/pic/1.jpg" align="left" width="3%" height="3%"></img>
<div style="display:flex;">

- Java JWT » 4.0.0

</div>
