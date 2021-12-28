<template>
  <div class="pt-4">
    <div>
    <b-container class="modal-content rounded-6 shadow" >
      <h1 style="text-align:center">Colestrol</h1>
      <b-table striped  :items="colestrol" :fields="fields" title="Colestrol" style="float:left;">
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-dark btn-sm"
            :to="`/biosinais/colestrol/${row.item.id}/updateColestrol`">Atualizar</nuxt-link>
          <button
            class="btn btn-danger btn-sm" cli
            @click="apagar(row.item.id)">Apagar</button>


        </template>
      </b-table>
      <b-row >
        <b-col lg="6" class="pb-4"><nuxt-link   class="btn btn-dark btn-sm" to="/biosinais/colestrol/createColestrol"  >Inserir Registo</nuxt-link></b-col>

      </b-row>
    </b-container>
    </div>
    <div class="pt-4">
      <b-container class="modal-content rounded-6 shadow" >
        <caption style="text-align:center">Gráfico</caption>
        <chartjs-line v-if="ready" v-bind:labels = "labels" v-bind:data="data"></chartjs-line>
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
      graphData: [],
      ready: false,
      labels: [],
      data: [],
      fields: [ 'date','value','classification','descricao','actions'],
      colestrol: [],
      user: null,


    }
  },
  created () {

    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/biosinais/colestrol/'+this.user.id)
          .then((colestrol) => {
            this.colestrol = colestrol
          })
        this.$axios.$get('/api/biosinais/colestrol/'+this.user.id+'/graph')
          .then((graph) => {
            this.labels = graph.label
            this.data = graph.data

            this.ready = true
            console.log( graph.data)
          })
      })
  },

  methods: {
  apagar: function (value){
    console.log(value+ " aqui ahahahaha")
    this.$axios.$delete('/api/biosinais/colestrol/'+value)
      .then(() => {
        this.$router.push('/biosinais/colestrol/my')



      })
  },

  }
}
</script>
