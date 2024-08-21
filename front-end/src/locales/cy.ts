import localeAdminHome from '../views/admin/locales/cy'
import localeAdminEventApprove from '../views/admin/approve/locales/cy'
import localeAdminUser from '../views/admin/user/locales/cy'
import localeAdminEditComponent from '../components/user/locales/cy'
import localeVolunteerHome from '../views/volunteer/locales/cy'
import organizerLocales from '../views/organizer/locales/cy'

const cy = {
  appName: 'Canolfan Wirfoddolwyr WSA',
  language: {
    chinese: 'Chinese',
    english: 'English',
    welsh: 'Welsh'
  },
  header: {
    actions: {
      locale: 'Newidwyd yr iaith yn llwyddiannus'
    }
  },
  menu: {
    manage: 'Rheoli',
    role: 'Rôl',
    user: 'Defnyddiwr',
    rewardstore: 'Storfa Gwobrwyo',
    approve: 'Cymeradwyo',
    event: 'Digwyddiad',
    organizer: 'Trefnydd',
    system: 'System',
    menu: 'Dewislen',
    dict: 'Geiriadur',
    home: 'Cartref',
    mail: 'Post',
    message: 'Neges',
    personal: 'Personol',
    password: 'Cyfrinair',
    exit: 'Allanfa',
    analyse: 'Dadansoddi',
    eventData: 'Data Digwyddiad',
    dataCount: 'Cyfrif Data',
    registerVolunteer: 'Cofrestru Gwirfoddolwyr',
    activeUser: 'Defnyddiwr Gweithgar',
    organizerData: 'Data Trefnydd'
  },
  login: {
    volunteer: 'Gwirfoddolwr',
    organizer: 'Trefnydd',
    admin: 'Gweinyddwr',
    register: 'Cofrestru',
    volunteerLogin: 'Mewngofnodi Gwirfoddolwr',
    username: 'Cyfrif',
    password: 'Cyfrinair',
    login: 'Mewngofnodi',
    reset: 'Ailosod',
    forgetPassword: 'Wedi anghofio cyfrinair?',
    usernameRequired: 'Rhowch enw defnyddiwr',
    passwordRequired: 'Rhowch gyfrinair'
  },
  register: {
    title: 'Cofrestru',
    username: 'Enw Defnyddiwr',
    password: 'Cyfrinair',
    email: 'E-bost',
    role: 'Rôl',
    register: 'Cofrestru',
    reset: 'Ailosod',
    usernameRequired: 'Rhowch enw defnyddiwr',
    passwordRequired: 'Rhowch gyfrinair',
    emailRequired: 'Rhowch gyfeiriad e-bost',
    emailInvalid: 'Rhowch gyfeiriad e-bost dilys',
    roleRequired: 'Dewiswch rôl',
    agreeTo: 'Rwy\'n cytuno i\'r',
    privacyPolicy: 'Polisi Preifatrwydd',
    agreementRequired: 'Mae’n rhaid i chi gytuno i’r polisi preifatrwydd i gofrestru.'
  },
  banner: {
    slogan1: 'Croeso i Ganolfan Wirfoddolwyr WSA',
    subSlogan1: 'helo',
    slogan2: 'Ennill talebau a gwobrau gwirfoddolwr',
    subSlogan2: 'diolch enfawr',
    slogan3: 'Rydym yma i helpu\'r gymuned chwaraeon',
    subSlogan3: 'gallwch wneud gwahaniaeth'
  },
  notification: {
    title: 'Hysbysiad',
    registrationFailed: 'Methwyd â chofrestru'
  },
  logout: {
    title: 'Hysbysiad System',
    message: 'A ydych yn cadarnhau eich bod am allgofnodi?',
    confirm: 'Cadarnhau',
    cancel: 'Canslo'
  },
  ...localeAdminHome,
  ...localeAdminEventApprove,
  ...localeAdminUser,
  ...localeAdminEditComponent,
  ...localeVolunteerHome,
  ...organizerLocales,
}

export { cy }
