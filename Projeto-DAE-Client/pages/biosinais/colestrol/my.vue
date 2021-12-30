<template>
  <div class="pt-4">
    <div>
    <b-container class="modal-content rounded-6 shadow" >
      <h1 style="text-align:center">Colestrol</h1>
      <b-table  :items="colestrol" :fields="fields" title="Colestrol" style="float:left;">
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
        <table class="table">
          <thead>
          <tr>
            <th scope="col"></th>
            <th scope="col">Geral</th>
            <th scope="col">Estatisticas classificações</th>
            <th scope="col">Handle</th>

          </tr>
          </thead>
          <tbody>
          <tr>
            <th scope="col"></th>
            <th>Nº de registos:</th>
            <th></th>
            <th>@mdo</th>
          </tr>
          <tr>
            <th scope="col"></th>
            <th>Nº de Altos:</th>
            <th>Nº de Medios:</th>
            <th>Nº de baixos:</th>
          </tr>
          <tr>
            <th scope="row"></th>
            <td colspan="2">Larry the Bird</td>
            <td>@twitter</td>
          </tr>
          </tbody>
        </table>
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

      fields: [
        {
          key: 'date',
          label: 'Data',
        },
        {
          key: 'value[0]',
          label: 'Nivel de Colestrol',
        },
        {
          key: 'classification',
          label: 'Classificação',
        },
        {
          key: 'descricao',
          label: 'Descrição',
        },
        {
          key: 'actions',
          label: 'Actions',

        }
      ],
      colestrol: [],
      user: null,


    }
  },
  created () {
    console.log(this.user)
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

          })
      })
  },

  methods: {
  apagar: function (value){

    this.$axios.$delete('/api/biosinais/colestrol/'+value)
      .then(() => {
        this.$axios.$get('/api/biosinais/colestrol/'+this.user.id)
          .then((colestrol) => {
            this.colestrol = colestrol
          })



      })
  },

  }
}
</script>
