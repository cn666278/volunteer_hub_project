import localeAdminHome from '../views/admin/locales/zh'
import localeAdminEventApprove from '../views/admin/approve/locales/zh'
import localeAdminUser from '../views/admin/user/locales/zh'
import localeAdminEditComponent from '../components/user/locales/zh'
import localeVolunteerHome from '../views/volunteer/locales/zh'
import organizerLocales from '../views/organizer/locales/zh'

const zh = {
  appName: 'WSA 志愿者中心',
  language: {
    chinese: '中文',
    english: '英文',
    welsh: '威尔士语'
  },
  header: {
    actions: {
      locale: '语言切换成功'
    }
  },
  menu: {
    manage: '管理',
    role: '角色',
    user: '用户',
    rewardstore: '积分商城',
    approve: '审批',
    event: '事件',
    organizer: '组织者',
    system: '系统',
    menu: '菜单',
    dict: '字典',
    home: '主页',
    mail: '邮件',
    message: '消息',
    personal: '个人',
    password: '密码',
    exit: '退出',
    analyse: '分析',
    eventData: '事件数据',
    dataCount: '数据统计',
    registerVolunteer: '志愿者注册',
    activeUser: '活跃用户',
    organizerData: '组织者数据'
  },
  login: {
    volunteer: '志愿者',
    organizer: '组织者',
    admin: '管理员',
    register: '注册',
    volunteerLogin: '志愿者登录',
    username: '用户名',
    password: '密码',
    login: '登录',
    reset: '重置',
    forgetPassword: '忘记密码？',
    usernameRequired: '请输入用户名',
    passwordRequired: '请输入密码'
  },
  register: {
    title: '注册',
    username: '用户名',
    password: '密码',
    email: '邮箱',
    role: '角色',
    register: '注册',
    reset: '重置',
    usernameRequired: '请输入用户名',
    passwordRequired: '请输入密码',
    emailRequired: '请输入邮箱地址',
    emailInvalid: '请输入有效的邮箱地址',
    roleRequired: '请选择一个角色',
    agreeTo: '我同意',
    privacyPolicy: '隐私政策',
    agreementRequired: '您必须同意隐私政策才能注册'
  },
  banner: {
    slogan1: '欢迎来到WSA志愿者中心',
    subSlogan1: '你好',
    slogan2: '获得志愿者代金券和奖励',
    subSlogan2: '衷心感谢',
    slogan3: '我们在这里帮助体育社区',
    subSlogan3: '你可以有所作为'
  },
  notification: {
    title: '通知',
    registrationFailed: '注册失败'
  },
  logout: {
    title: '系统通知',
    message: '您确定要退出吗？',
    confirm: '确认',
    cancel: '取消'
  },
  ...localeAdminHome,
  ...localeAdminEventApprove,
  ...localeAdminUser,
  ...localeAdminEditComponent,
  ...localeVolunteerHome,
  ...organizerLocales,
}

export { zh }
