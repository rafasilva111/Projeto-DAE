<template>
  <div class="chart-div">
    <LineChart :chardata="charData" :options="chartOptions" class="line-chart">
    </LineChart>
  </div>

</template>

<script>
import LineChart from "@/components/LineChart";
export default {
  components: {LineChart},
  data(){
    return {
      charData: {
        labels: ["Jan","Fev","Mar","Abr"],
        datasets:[
          {
          label: "Numbers",
          borderColor: "#000000",
          borderWidth: 4,
          fill: false,
          data: [100,150,400,300]
          },
        ],
      },
      chartOptions: {
        maintainAspectRatio: false,
        reponsive: true
      }
    }
  }
}
</script>

<style>
.line-chart{
  width: 700px;
  height: 500px;
}
</style>
