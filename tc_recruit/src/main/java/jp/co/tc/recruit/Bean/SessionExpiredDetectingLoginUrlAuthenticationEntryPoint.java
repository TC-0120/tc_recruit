package jp.co.tc.recruit.Bean;

public class SessionExpiredDetectingLoginUrlAuthenticationEntryPoint /*extends LoginUrlAuthenticationEntryPoint*/{
	/*@Override
	   protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

	       String redirectUrl = super.buildRedirectUrlToLoginPage(request, response, authException);
	       if (isRequestedSessionInvalid(request)) {
	           redirectUrl += redirectUrl.contains("?") ? "&" : "?";
	           redirectUrl += "timeout";
	       }
	       return redirectUrl;
	   }

	   private boolean isRequestedSessionInvalid(HttpServletRequest request) {
	       return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
	   }

	   //Ajaxの場合
	   @Override
	   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

	       if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	           return;
	       }

	       super.commence(request, response, authException);
	   }

	   public SessionExpiredDetectingLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
	       super(loginFormUrl);
	   }*/
}
