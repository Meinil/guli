<template>
    <div class="cart py-container">
        <!--主内容-->
        <div class="checkout py-container pay">
            <div class="checkout-tit">
                <h4 class="fl tit-txt">
                    <span class="success-icon"></span
                    ><span class="success-info"
                        >订单提交成功，请您及时付款！订单号：{{
                            payInfo.out_trade_no
                        }}</span
                    >
                </h4>
                <span class="fr"
                    ><em class="sui-lead">应付金额：</em
                    ><em class="orange money"
                        >￥{{ payInfo.total_fee }}</em
                    ></span
                >
                <div class="clearfix"></div>
            </div>
            <div class="checkout-steps">
                <div class="fl weixin">微信支付</div>
                <div class="fl sao">
                    <p class="red">请使用微信扫一扫。</p>
                    <div class="fl code">
                        <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
                        <!-- <qriously value="weixin://wxpay/bizpayurl?pr=R7tnDpZ" :size="338"/> -->
                        <qriously :value="payInfo.code_url" :size="338" />
                        <div class="saosao">
                            <p>请使用微信扫一扫</p>
                            <p>扫描二维码支付</p>
                        </div>
                    </div>
                </div>
                <div class="clearfix"></div>
                <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->
            </div>
        </div>
    </div>
</template>
<script>
import orderApi from "@/api/order";
export default {
    asyncData({params, error}) {
        return orderApi.createNative(params.id)
        .then(res => {
            return {
                payInfo: res.data,
                orderNo: params.id
            }
        })
    },
    data() {
        return {
            result: 0 //定时器
        }
    },
    mounted() {
        this.result = setInterval(() => {
            this.queryOrderStatus()
        }, 3000)
    },
    methods: {
        queryOrderStatus() {
            orderApi.queryPayStatus(this.orderNo)
            .then(res => {
                if (res.message == "支付成功") {
                    clearInterval(this.result)
                    this.$message({
                        type: "success",
                        message: "支付成功"
                    })
                    this.$router.push({
                        path: `/course/${this.payInfo.course_id}`
                    })
                }
            })
        }
    }
};
</script>