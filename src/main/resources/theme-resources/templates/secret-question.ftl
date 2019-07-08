<form id="kc-totp-login-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
    <div class="${properties.kcFormGroupClass!}">
        <div class="${properties.kcLabelWrapperClass!}">
            <label for="totp" class="${properties.kcLabelClass!}">What's your student number?</label>
        </div>

        <div class="${properties.kcInputWrapperClass!}">
            <input id="totp" name="secret_answer" type="text" class="${properties.kcInputClass!}" />
        </div>
    </div>
</form>