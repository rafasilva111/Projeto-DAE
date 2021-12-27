<template>
  <div margin-top="100px" class="pt-4">
    <div>
    <b-container class="modal-content rounded-6 shadow" >
      <caption style="text-align:center">Colestrol</caption>
      <b-table striped  :items="colestrol" :fields="fields" title="Colestrol" style="float:left;">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-link"
            :to="`/students/${row.item.username}/details`">Details</nuxt-link>
          <nuxt-link
            class="btn btn-link"
            :to="`/students/${row.item.username}/updateStudent`">Update</nuxt-link>

        </template>
      </b-table>
      <b-row >
        <b-col lg="6" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/"  >Inserir Registo</nuxt-link></b-col>

      </b-row>
    </b-container>
    </div>
    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <caption style="text-align:center">Gráfico</caption>
        <chartjs-line v-bind:labels = "labels" v-bind:data="data"></chartjs-line>
      </b-container>
    </div>
    <div class="pt-4 pb-4">
      <b-container class="modal-content rounded-6 shadow" >
        <caption style="text-align:center">Estatísticas</caption>

      </b-container>
    </div>


  </div>



</template>

<script>

export default {
  data () {
    return {
      labels: [1,2,3,4],
      data: [10,7,3,20],
      fields: [ 'date','value','classification'],
      colestrol: [],
      bpm: [],
      pesagem: [],
      outro: [],
      user: null,

    }
  },


  created () {
    console.log(this.$auth.user)
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
      })
    console.log(this.user)
  }
}
</script>
