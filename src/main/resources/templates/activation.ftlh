<style>
    .activation__wrapper {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate( -50%, -50%);
    }
    .activation__container {
        background-color: rgb(255,255,255);
        box-shadow: 0 0 5px 0 rgba(0,0,0,0.3);
        border-radius: 3px;
        width: 420px;
        height: auto;
        font-family: 'Open Sans';
        text-align: center;
    }
    .activation__header {
        width: 100%;
        height: 60px;
        padding: 10px 0;
    }
    .activation__logo{
        width: 250px;
    }
    .activation__subject {
        height: 40px;
        text-align: center;
        font-size: 15px;
        font-weight: 600;
    }
    .activation__arrow {
        position: relative;
        background: transparent;
    }
    .activation__arrow:after {
        top: 100%;
        left: 50%;
        border: solid transparent;
        content: " ";
        height: 0;
        width: 0;
        position: absolute;
        pointer-events: none;
        border-color: rgba(255, 255, 255, 0);
        border-top-color: #fff;
        border-width: 20px;
        margin-left: -20px;
    }
    .activation__message {
        background-color: rgb(248,248,248);
        height: 60px;
        line-height: 25px;
        padding: 30px 10px 10px;
        font-size: 15px;
    }
    .activation__user {
        font-weight: 600;
        font-size: 18px;
    }
    .activation__link {
        background-color: rgb(255,255,255);
        height: 100px;
        font-size: 15px;
        line-height: 25px;
        padding: 20px 10px;
    }
    .activation__btn {
        border-radius: 3px;
        color: rgb(255,255,255);
        font-size: 15px;
        background: rgb(21,142,255);
        padding: 10px 20px;
        text-decoration: none;
        width: 200px;
        margin: 10px auto 0;
        cursor: pointer;
    }
    .activation__btn:hover {
        background: #2997ff; /*lighten(rgb(21,142,255), 3)*/
        text-decoration: none;
    }
    .activation__footer {
        font-size: 13px;
        line-height: 20px;
        height: 50px;
        padding: 10px 0;
    }
</style>

<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />

<div class="activation__wrapper">
    <div class="activation__container">
        <div class="activation__subject">Dummies Activation Link</div>
        <div class="activation__arrow"></div>
        <div class="activation__message">
            <div> Dear, <span class="activation__user">${username}</span>

                <br/>Thank you for choosing Dummies</div>
        </div>
        <div class="activation__link">
            <div>
                Click on the following link to activate your account:
            </div>
            <a href="${activation_link}" target="_blank" class="activation__btn">Activate your account here</a>
        </div>
        <div class="activation__footer">
            Thank you. Sincerely,
            <br/> Dummies Support Team
            <br/>
        </div>
    </div>
</div>