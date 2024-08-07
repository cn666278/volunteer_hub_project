// import qs from 'query-string';
import type { DescData } from '@arco-design/web-vue/es/descriptions/interface';

export interface EventType {
  id: string;
  organizerId: number;
  number: number;
  title: string;  
  description: string;  
  location: string;  
  pointsAwarded: number;  
  eventType: 'Judo' | 'Badminton' | 'Table Tennis' | 'Hockey' | 'Cycling' | 'Snowsports';
  filterType: 'artificial' | 'rules';
  count: number;
  status: 'Awaiting Review' | 'Passed';
  startDate: string;  
  endDate: string;  
  eventPic: string;  
}

export interface PolicyParams extends Partial<EventType> {
  current: number;
  pageSize: number;
}

export interface PolicyListRes {
  list: EventType[];
  total: number;
}


export interface ServiceRecord {
  id: number;
  title: string;
  description: string;
  name?: string;
  actionType?: string;
  icon?: string;
  data?: DescData[];
  enable?: boolean;
  expires?: boolean;
}
