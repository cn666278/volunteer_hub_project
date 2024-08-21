import localeAdminHome from '../views/admin/locales/en'
import localeAdminEventApprove from '../views/admin/approve/locales/en'
import localeAdminUser from '../views/admin/user/locales/en'
import localeAdminEditComponent from '../components/user/locales/en'
import localeVolunteerHome from '../views/volunteer/locales/en'
import organizerLocales from '../views/organizer/locales/en'

const en = {
  appName: 'WSA Volunteer Hub',
  language: {
    chinese: 'Chinese',
    english: 'English',
    welsh: 'Welsh'
  },
  header: {
    actions: {
      locale: 'Language switched successfully'
    }
  },
  menu: {
    manage: 'Manage',
    role: 'Role',
    user: 'User',
    rewardstore: 'RewardStore',
    approve: 'Approve',
    event: 'Event',
    organizer: 'Organizer',
    system: 'System',
    menu: 'Menu',
    dict: 'Dictionary',
    home: 'Home',
    mail: 'Mail',
    message: 'Message',
    personal: 'Personal',
    password: 'Password',
    exit: 'Exit',
    analyse: 'Analyse',
    eventData: 'Event Data',
    dataCount: 'Data Count',
    registerVolunteer: 'Register Volunteer',
    activeUser: 'Active User',
    organizerData: 'Organizer Data'
  },
  login: {
    volunteer: 'Volunteer',
    organizer: 'Organizer',
    admin: 'Admin',
    register: 'Register',
    volunteerLogin: 'Volunteer Login',
    username: 'Username',
    password: 'Password',
    login: 'Login',
    reset: 'Reset',
    forgetPassword: 'Forget Password?',
    usernameRequired: 'Please enter the username',
    passwordRequired: 'Please enter the password'
  },
  register: {
    title: 'Registration',
    username: 'Username',
    password: 'Password',
    email: 'Email',
    role: 'Role',
    register: 'Register',
    reset: 'Reset',
    usernameRequired: 'Please enter the username',
    passwordRequired: 'Please enter the password',
    emailRequired: 'Please enter the email address',
    emailInvalid: 'Please enter a valid email address',
    roleRequired: 'Please select a role'
  },
  banner: {
    slogan1: 'Welcome to WSA Volunteer Hub',
    subSlogan1: 'hello',
    slogan2: 'Earn volunteer vouchers and rewards',
    subSlogan2: 'a massive thank you',
    slogan3: 'We are here to help the sports community',
    subSlogan3: 'you can make a difference'
  },
  notification: {
    title: 'Notification',
    registrationFailed: 'Registration failed'
  },
  logout: {
    title: 'System Notification',
    message: 'Do you confirm to exit?',
    confirm: 'Confirm',
    cancel: 'Cancel'
  },
  ...localeAdminHome,
  ...localeAdminEventApprove,
  ...localeAdminUser,
  ...localeAdminEditComponent,
  ...localeVolunteerHome,
  ...organizerLocales,
}

export { en }
