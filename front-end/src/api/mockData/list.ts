import Mock from "mockjs";

const { Random } = Mock;

const data = Mock.mock({
  "list": [
    {
      "id|8": /[A-Z][a-z][-][0-9]/,
      "number|2-3": /[0-9]/,
      "name": "Welsh Judo are hosting the British Adaptive & VI Open in Swansea.",
      "eventType": "Judo",
      "count|2-3": /[0-9]/,
      "status|1": ["online", "offline"],
      "filterType|1": ["artificial", "rules"],
      createdTime: Random.datetime(),
    },
    {
      "id|8": /[A-Z][a-z][-][0-9]/,
      "number|2-3": /[0-9]/,
      "name": "Badminton Wales are hosting the Yonex Welsh International Challenge in Cardiff",
      "eventType": "Badminton",
      "count|2-3": /[0-9]/,
      "status|1": ["online", "offline"],
      "filterType|1": ["artificial", "rules"],
      createdTime: Random.datetime(),
    },
    {
      "id|8": /[A-Z][a-z][-][0-9]/,
      "number|2-3": /[0-9]/,
      "name": "Table Tennis Wales hosting the Senior Team Championships of Wales",
      "eventType": "Table Tennis",
      "count|2-3": /[0-9]/,
      "status|1": ["online", "offline"],
      "filterType|1": ["artificial", "rules"],
      createdTime: Random.datetime(),
    },
    {
      "id|8": /[A-Z][a-z][-][0-9]/,
      "number|2-3": /[0-9]/,
      "name": "Hockey Wales hosting International Series over a long weekend.",
      "eventType": "Hockey",
      "count|2-3": /[0-9]/,
      "status|1": ["online", "offline"],
      "filterType|1": ["artificial", "rules"],
      createdTime: Random.datetime(),
    },
    {
      "id|8": /[A-Z][a-z][-][0-9]/,
      "number|2-3": /[0-9]/,
      "name": "Welsh Cycling hosting the Masters National Track Championships in Newport",
      "eventType": "Cycling",
      "count|2-3": /[0-9]/,
      "status|1": ["online", "offline"],
      "filterType|1": ["artificial", "rules"],
      createdTime: Random.datetime(),
    },
    {
      "id|8": /[A-Z][a-z][-][0-9]/,
      "number|2-3": /[0-9]/,
      "name": "Snowsports Wales hosting the Welsh Outdoor Championships in Pontypool.",
      "eventType": "Snowsports",
      "count|2-3": /[0-9]/,
      "status|1": ["online", "offline"],
      "filterType|1": ["artificial", "rules"],
      createdTime: Random.datetime(),
    }
  ],
});

export default {
  getEventList: (config: any) => {
    // 后端分页
    const { current = 1, pageSize = 10 } = JSON.parse(config.body);
    const p = current as number;
    const ps = pageSize as number;
    return {
      code: 200,
      data: {
        list: data.list.slice((p - 1) * ps, p * ps),
        total: data.list.length,
      },
    };
  },
};
