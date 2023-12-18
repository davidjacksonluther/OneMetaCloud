import {createRouter,createWebHashHistory} from "vue-router";


const routes = [
    {path:'/:pathMatch(.*)*',component:()=>import("@/views/Error/404.Vue")},
    {path:'/Login/:username:',component: ()=>import('@/views/Account/Login.vue')}
]

const router = createRouter({
    history:createWebHashHistory(),
    routes:routes
});

export default router;

