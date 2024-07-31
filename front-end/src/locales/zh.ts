import localeAdminHome from '../views/admin/locales/zh'
import localeAdminEventApprove from '../views/admin/approve/locales/zh'
const zh = {
  appName: 'WSA 志愿者中心',
  language: {
    chinese: 'Chinese',
    english: 'English',
    welsh: 'Welsh'
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
    exit: '退出'
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
  banner: {
    slogan1: '欢迎来到WSA志愿者中心',
    subSlogan1: '你好',
    slogan2: '获得志愿者代金券和奖励',
    subSlogan2: '衷心感谢',
    slogan3: '我们在这里帮助体育社区',
    subSlogan3: '你可以有所作为'
  },
  notification: {
    title: '通知'
  },
  logout: {
    title: '系统通知',
    message: '您确定要退出吗？',
    confirm: '确认',
    cancel: '取消'
  },
  ...localeAdminHome,
  ...localeAdminEventApprove,
}
export { zh }
