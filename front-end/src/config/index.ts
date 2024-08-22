/**
 * Environment configuration file
 * In enterprise-level projects, there are usually multiple environments, such as development environment, test environment, production environment, etc.
 */

// Current environment variable
const env = import.meta.env.MODE || 'prod'


const EnvConfig: { [key: string]: { baseApi: string; mockApi: string } } = {
    development: { // Development environment
        baseApi: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081', // Interface request address
        // Use local data for mock data
        // baseApi: '/api',
        // Use apifox for mock data
        mockApi: 'https://apifoxmock.com/m1/4819087-4473805-default',
    },
    test: { // Test environment
        baseApi: '/test',
        mockApi: 'https://apifoxmock.com/m1/4819087-4473805-default'
    },
    prod: { // Production environment
        baseApi: '/prod',
        mockApi: 'https://apifoxmock.com/m1/4819087-4473805-default'
    }
}

export default {
    env,
    // Mock switch, whether to enable mock data function
    mock: false,
    ...EnvConfig[env]
}