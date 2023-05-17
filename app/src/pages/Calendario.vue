<template>
  <div class="calendario">
    <div class="header">
      <button @click="previousMonth">Mese precedente</button>
      <h2>{{ month }}</h2>
      <button @click="nextMonth">Mese successivo</button>
    </div>
    <table>
      <thead>
        <tr>
          <th v-for="day in daysOfWeek" :key="day">{{ day }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="week in calendar" :key="week">
          <td v-for="date in week" :key="date.date" :class="getCellClasses(date)" @click="changeColor(date)">
            <div class="date">{{ date.date }}</div>
            <div class="color-marker" :class="date.color"></div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { startOfMonth, endOfMonth, eachDayOfInterval, isSameMonth, addMonths, subMonths, startOfWeek } from 'date-fns';

export default {
  data() {
    return {
      daysOfWeek: ['Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab', 'Dom'],
      selectedDate: null,
      calendar: [],
      currentDate: new Date(),
    };
  },
  mounted() {
    this.generateCalendar();
  },
  computed: {
    month() {
      return this.currentDate.toLocaleString('default', { month: 'long', year: 'numeric' });
    },
  },
  methods: {
    generateCalendar() {
      const startOfMonthDate = startOfMonth(this.currentDate);
      const startOfWeekDate = startOfWeek(startOfMonthDate, { weekStartsOn: 1 });
      const endOfMonthDate = endOfMonth(this.currentDate);

      const days = eachDayOfInterval({
        start: startOfWeekDate,
        end: endOfMonthDate,
      });

      this.calendar = [];
      let week = [];

      days.forEach((date) => {
        const day = {
          date: date.getDate(),
          month: date.getMonth(),
          year: date.getFullYear(),
          color: '',
        };

        week.push(day);

        if (date.getDay() === 0) {
          this.calendar.push(week);
          week = [];
        }
      });

      if (week.length > 0) {
        this.calendar.push(week);
      }
    },
    getCellClasses(date) {
      return {
        'selected': isSameMonth(date, this.selectedDate),
      };
    },
    changeColor(date) {
      if (date.color === 'red') {
        date.color = 'green';
      } else if (date.color === 'green') {
        date.color = 'gray';
      } else {
        date.color = 'red';
      }
    },
    previousMonth() {
      this.currentDate = subMonths(this.currentDate, 1);
      this.generateCalendar();
    },
    nextMonth() {
      this.currentDate = addMonths(this.currentDate, 1);
      this.generateCalendar();
    },
  },
};
</script>

<style scoped>
.calendario {
  max-width: 800px;
  margin: 0 auto;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
  td {
    text-align: center;
    padding: 10px;
    border: 1px solid #ccc;
  }

  th {
    background-color: #f2f2f2;
  }

  td {
    cursor: pointer;
    position: relative; /* Added position property */
  }

  .date {
    margin-bottom: 5px;
  }

  .color-marker {
    position: absolute;
    bottom: 5px;
    left: 50%;
    transform: translateX(-50%);
    width: 10px;
    height: 10px;
    border-radius: 50%;
  }

  .red {
    background-color: #ff9999;
  }

  .green {
    background-color: #a3e6a8;
  }

  .gray {
    background-color: #ccc;
  }
</style>
