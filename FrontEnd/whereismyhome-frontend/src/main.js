import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

const app = createApp(App);
const pinia = createPinia();

// app.use(createPinia()); // 대실수............ 새로고침해도 데이터(store)를 유지할 수 있게 pinia.use(piniaPluginPersistedstate); 해줬는데 새로 생성한 피니아 연결해서 sessionStorage에 memberStore도 안뜨고 새로고침하면 데이터 날라감;ㅠ
app.use(router);
app.use(pinia);
pinia.use(piniaPluginPersistedstate);

app.mount("#app");
