import localeAdminHome from '../views/admin/locales/cy'
import localeAdminEventApprove from '../views/admin/approve/locales/cy'
import localeAdminUser from '../views/admin/user/locales/cy'
import localeAdminEditComponent from '../components/user/locales/cy'
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
      exit: 'Allanfa'
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
    banner: {
      slogan1: 'Croeso i Ganolfan Wirfoddolwyr WSA',
      subSlogan1: 'helo',
      slogan2: 'Ennill talebau a gwobrau gwirfoddolwr',
      subSlogan2: 'diolch enfawr',
      slogan3: 'Rydym yma i helpu\'r gymuned chwaraeon',
      subSlogan3: 'gallwch wneud gwahaniaeth'
    },
    notification: {
      title: 'Hysbysiad'
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
  }
  export { cy }
  